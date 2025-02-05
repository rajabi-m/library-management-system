package org.example.view;

import org.example.io.OutputDisplay;
import org.example.manager.MagazineManager;
import org.example.model.AssetStatus;
import org.example.model.Library;

import java.util.Scanner;

public class MagazineMenuView extends MenuView{
    private final MagazineManager magazineManager;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Add Magazine", "Add a magazine to the library", this::addMagazineCommand),
            new CommandTemplate("Remove Magazine", "Remove a magazine from the library", this::removeMagazineCommand),
            new CommandTemplate("Update Magazine Status", "Update the status of a magazine", this::updateMagazineStatusCommand),
            new CommandTemplate("Get all magazines", "Get all magazines in the library", this::getAllMagazinesCommand),
    };

    private String getAllMagazinesCommand() {
        return magazineManager.getAllMagazines().toString();
    }

    private String updateMagazineStatusCommand() {
        System.out.println("Enter magazine title: ");
        String title = scanner.nextLine();

        System.out.println("Enter magazine publisher: ");
        String publisher = scanner.nextLine();

        System.out.println("Enter magazine release date: ");
        String releaseDate = scanner.nextLine();

        System.out.println("Enter magazine status: ");
        String status = scanner.nextLine();

        return magazineManager.updateMagazineStatus(title, publisher, releaseDate, AssetStatus.valueOf(status));
    }

    private String removeMagazineCommand() {
        System.out.println("Enter magazine title: ");
        String title = scanner.nextLine();

        System.out.println("Enter magazine publisher: ");
        String publisher = scanner.nextLine();

        System.out.println("Enter magazine release date: ");
        String releaseDate = scanner.nextLine();

        return magazineManager.removeMagazine(title, publisher, releaseDate);
    }

    private String addMagazineCommand() {
        System.out.println("Enter magazine title: ");
        String title = scanner.nextLine();

        System.out.println("Enter magazine publisher: ");
        String publisher = scanner.nextLine();

        System.out.println("Enter magazine release date: ");
        String releaseDate = scanner.nextLine();

        return magazineManager.addMagazine(title, publisher, releaseDate);
    }

    protected MagazineMenuView(Library library, Scanner scanner, OutputDisplay outputDisplay) {
        super(scanner, outputDisplay);
        addCommands(commands);
        magazineManager = new MagazineManager(library);
    }
}
