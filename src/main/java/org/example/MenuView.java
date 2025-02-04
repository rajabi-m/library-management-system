package org.example;

import org.example.model.OutputDisplay;

import java.util.Scanner;
import java.util.concurrent.Callable;

public abstract class MenuView {
    private boolean isMenuRunning = true;
    protected final Scanner scanner;
    protected final OutputDisplay outputDisplay;
    private CommandTemplate[] commands = {
            new CommandTemplate("Help", "Display the list of available commands", this::availableCommandsString),
            new CommandTemplate("Exit", "Exit the Menu", this::exitCommand)
    };

    protected MenuView(Scanner scanner, OutputDisplay outputDisplay) {
        this.scanner = scanner;
        this.outputDisplay = outputDisplay;
    }

    public void run() {
        onMenuInitialize();
        printCommandList();
        while (isMenuRunning){
            System.out.print("> ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            handleMenuChoice(choice);
        }
    }

    protected void addCommands(CommandTemplate[] newCommands){
        CommandTemplate[] mergedCommands = new CommandTemplate[newCommands.length + commands.length];
        System.arraycopy(newCommands, 0, mergedCommands, 0, newCommands.length);
        System.arraycopy(commands, 0, mergedCommands, newCommands.length, commands.length);
        this.commands = mergedCommands;
    }

    protected void onMenuInitialize(){};

    private void printCommandList(){
        System.out.println(availableCommandsString());
    }

    private void handleMenuChoice(int choice){
        if (choice < 1 || choice > commands.length){
            outputDisplay.display("Invalid choice!");
            return;
        }

        try {
            String result = commands[choice - 1].function.call();

            String output =
                    "Operation: " + commands[choice - 1].commandName + "\n" +
                            "Result:\n" +
                            result;

            outputDisplay.display(output);

        } catch (Exception exception){
            outputDisplay.display("An error occurred: " + exception.getMessage());
        }
    }

    private String availableCommandsString(){
        // Create String with all available commands
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < commands.length; i++) {
            output.append("    ").append(i + 1).append(". ").append(commands[i].commandName).append(" - ").append(commands[i].commandDescription).append("\n");
        }
        return output.toString();
    }

    private String exitCommand() {
        isMenuRunning = false;
        return "Exiting the program!";
    }

    protected record CommandTemplate(String commandName, String commandDescription, Callable<String> function) {}
}
