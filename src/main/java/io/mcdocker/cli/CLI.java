package io.mcdocker.cli;

import io.mcdocker.cli.commands.containers.Container;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
//        name = "mcdocker",
//        description = "A command line interface for managing MCDocker containers",
//        version = "0.0.1",
//        mixinStandardHelpOptions = true
)
public class CLI implements Runnable {

    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new CLI());

        cli.addSubcommand(new Container());

        int exitCode = cli.execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Hello, world!");
    }
}
