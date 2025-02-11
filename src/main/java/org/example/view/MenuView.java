package org.example.view;

import org.example.io.OutputDisplay;

import java.util.InputMismatchException;
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

    public void run() throws Exception{
        onMenuInitialize();
        printCommandList();
        while (isMenuRunning){
            System.out.print("> ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                handleMenuChoice(choice);
            } catch (InputMismatchException e){
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
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

    private void handleMenuChoice(int choice) throws Exception{
        if (choice < 1 || choice > commands.length){
            outputDisplay.display("Invalid choice!");
            return;
        }

        String result = commands[choice - 1].function.call();

        if (result == null) {
            return;
        }

        String output =
                "Operation: " + commands[choice - 1].commandName + "\n" +
                        "Result:\n" +
                        result;

        outputDisplay.display(output);
    }

    protected String availableCommandsString(){
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
