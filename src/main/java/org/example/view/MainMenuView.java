package org.example.view;

import org.example.io.OutputDisplay;
import org.example.model.*;

import java.util.Scanner;

public class MainMenuView extends MenuView{
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Enter Books Menu", "Enter the books menu", this::enterBooksMenuCommand),
            new CommandTemplate("Enter Magazines Menu", "Enter the magazines menu", this::enterMagazinesMenuCommand),
            new CommandTemplate("Get all assets", "Get all assets in the library", this::getAllAssetsCommand),
            new CommandTemplate("Get assets by title", "Get all assets with a specific title", this::getAssetsByTitleCommand),
    };

    private final Library library;

    public MainMenuView(Library library, OutputDisplay outputDisplay){
        super(new Scanner(System.in), outputDisplay);
        addCommands(commands);
        this.library = library;
    }

    @Override
    protected void onMenuInitialize() {
        System.out.println(welcomeText);
    }

    private String enterBooksMenuCommand() {
        BookMenuView bookMenuView = new BookMenuView(library, scanner, outputDisplay);
        bookMenuView.run();
        return availableCommandsString();
    }

    private String enterMagazinesMenuCommand() {
        MagazineMenuView magazineMenuView = new MagazineMenuView(library, scanner, outputDisplay);
        magazineMenuView.run();
        return availableCommandsString();
    }

    private String getAssetsByTitleCommand() {
        System.out.println("Enter asset title: ");
        String title = scanner.nextLine();

        var books = library.getAssetsByTitle(title);

        return books.toString();
    }

    private String getAllAssetsCommand() {
        return library.getAssets().toString();
    }
}