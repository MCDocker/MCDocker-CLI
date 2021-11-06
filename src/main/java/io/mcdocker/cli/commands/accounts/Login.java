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

package io.mcdocker.cli.commands.accounts;

import com.google.gson.GsonBuilder;
import io.mcdocker.launcher.MCDocker;
import io.mcdocker.launcher.auth.Account;
import io.mcdocker.launcher.auth.impl.MicrosoftAuth;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.CompletableFuture;

@Command(name = "login", description = "Login to your account")
public class Login implements Runnable {

    @Command(name = "microsoft", description = "Login with a Microsoft account")
    public void microsoftLogin(@Option(names = {"-o", "--output"}, description = "Output information") boolean output) {


        MCDocker.startServer();
        MicrosoftAuth auth = new MicrosoftAuth();

        CompletableFuture<Account> accountCompletableFuture = output ? auth.authenticate(System.out::println) : auth.authenticate();

        CompletableFuture<Account> future = accountCompletableFuture.whenComplete((account, throwable) -> {
            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(account));
        });

        future.join();

    }

    @Option(names = {"-u", "--username"}, description = "Username")
    String username;

    @Option(names = {"-p", "--password"}, description = "Password")
    String password;

    @Override
    public void run() {

    }
}
