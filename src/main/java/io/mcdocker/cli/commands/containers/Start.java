/*
 *
 *   MCDocker, an open source Minecraft launcher.
 *   Copyright (C) 2021 MCDocker
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.mcdocker.cli.commands.containers;

import io.mcdocker.launcher.auth.Account;
import io.mcdocker.launcher.auth.AccountsManager;
import io.mcdocker.launcher.auth.Authentication;
import io.mcdocker.launcher.auth.impl.OfflineAuth;
import io.mcdocker.launcher.container.Container;
import io.mcdocker.launcher.content.clients.Client;
import io.mcdocker.launcher.content.clients.ClientManifest;
import io.mcdocker.launcher.content.clients.impl.vanilla.Vanilla;
import io.mcdocker.launcher.content.clients.impl.vanilla.VanillaClient;
import io.mcdocker.launcher.content.clients.impl.vanilla.VanillaManifest;
import io.mcdocker.launcher.launch.LaunchWrapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Command(name = "start", description = "Start a container")
public class Start implements Runnable{

    @Parameters(index = "0", description = "The id of the container")
    String id;

    @Option(names = {"-o", "--output"}, defaultValue = "true", description = "Display Minecraft output")
    boolean output;

    @Override
    public void run() {
        Container container = Container.getContainerById(id);
        if (container == null) {
            System.out.println("Could not find container '" + id + "'");
            return;
        }

        Account account = AccountsManager.getInstance().getCurrentAccount();

        VanillaManifest client = container.getDockerfile().getClient(VanillaManifest.class);

        try {
            Client<?> c = Client.of(client);
            LaunchWrapper launchWrapper = new LaunchWrapper(container, c);
            Process process = launchWrapper.launch(account).get();

            System.out.println("PID " + process.pid());

            if(output) {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = input.readLine()) != null) System.out.println(line);
                input.close();

                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorLine;
                while ((errorLine = error.readLine()) != null) System.out.println(errorLine);
                input.close();
            }

            process.onExit().thenRun(() -> {
                if(process.exitValue() != 0) {
                    System.err.println("Container exited with error code " + process.exitValue());
                } else {
                    System.out.println("Container exited");
                }
            });

        } catch (IOException | InterruptedException | ExecutionException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
