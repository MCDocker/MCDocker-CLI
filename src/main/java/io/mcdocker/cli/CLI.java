package io.mcdocker.cli;

import io.mcdocker.cli.commands.ContainerCommand;
import io.mcdocker.launcher.MCDocker;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        description = "A command line interface for managing MCDocker containers",
        mixinStandardHelpOptions = true,
        name = "mcdocker"
)
public class CLI implements Runnable {

    private static final CommandLine cli = new CommandLine(new CLI());
    private static final String version = "0.0.1";
    public static String getVersion() {return version;}

    public static void main(String[] args) {
        cli.addSubcommand(new ContainerCommand());

        int exitCode = cli.execute(args);
        System.exit(exitCode);
    }

    @Command(name = "version", description = "Prints the MCDocker and CLI version")
    public void version() {
        System.out.println("CLI " + version);
        System.out.println("MCDocker " + MCDocker.getVersion());
    }

    @Override
    public void run() {
        System.out.println("MCDocker-CLI - A command line interface for MCDocker");
    }
}
