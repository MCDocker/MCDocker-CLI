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

import com.google.gson.Gson;
import io.mcdocker.cli.utils.TableCreator;
import io.mcdocker.launcher.container.Container;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

@Command(name = "list", description = "List all containers")
public class ListContainers implements Runnable {

    @Option(names = {"--json"}, description = "Output in JSON format")
    boolean json;

    @Override
    public void run() {
        List<Container> containers = Container.getContainers();

        if(json) {
            System.out.println(new Gson().toJson(containers));
            return;
        }

        TableCreator creator = TableCreator.getInstance();
        creator.setHeaders("Name", "ID");
        for (Container container : containers) {
            creator.addRow(container.getDockerfile().getName(), container.getDockerfile().getId().toString());
        }

        System.out.println(creator.getOutput());

    }
}
