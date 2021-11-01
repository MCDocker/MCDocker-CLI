package io.mcdocker.cli.commands.containers;

import picocli.CommandLine.Command;

@Command(name = "containers", description = "Container management", mixinStandardHelpOptions = true)
public class Container {

    @Command(name = "create", description = "Creates a new container")
    public void create() {
        System.out.println("create");
    }

}
