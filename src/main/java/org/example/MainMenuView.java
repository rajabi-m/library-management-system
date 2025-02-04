package org.example;

import org.example.model.*;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class MainMenuView extends MenuView{
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Enter Books Menu", "Enter the books menu", this::enterBooksMenuCommand),
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
//        BooksMenuView booksMenuView = new BooksMenuView(library, scanner);
//        booksMenuView.run();
        return "Not Implemented Yet";
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