package org.example.view;

import org.example.io.OutputDisplay;
import org.example.manager.BookManager;
import org.example.model.*;

import java.util.Scanner;

public class BookMenuView extends MenuView{

    private final BookManager bookManager;

    private final CommandTemplate[] commands = {
            new CommandTemplate("Add Book", "Add a book to the library", this::addBookCommand),
            new CommandTemplate("Remove Book", "Remove a book from the library", this::removeBookCommand),
            new CommandTemplate("Update Book Status", "Update the status of a book", this::updateBookStatusCommand),
            new CommandTemplate("Get all books", "Get all books in the library", this::getAllBooksCommand),
    };

    protected BookMenuView(Library library, Scanner scanner, OutputDisplay outputDisplay) {
        super(scanner, outputDisplay);
        addCommands(commands);
        this.bookManager = new BookManager(library);
    }

    private String addBookCommand() {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();

        System.out.println("Enter book author: ");
        String author = scanner.nextLine();

        System.out.println("Enter book release year: ");
        int releaseYear = scanner.nextInt();
        scanner.nextLine();

        return bookManager.addBook(title, author, releaseYear);
    }

    private String removeBookCommand() {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();

        System.out.println("Enter book author: ");
        String author = scanner.nextLine();

        System.out.println("Enter book release year: ");
        int releaseYear = scanner.nextInt();
        scanner.nextLine();

        return bookManager.removeBook(title, author, releaseYear);
    }

    private String updateBookStatusCommand() {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();

        System.out.println("Enter book author: ");
        String author = scanner.nextLine();

        System.out.println("Enter book release year: ");
        int releaseYear = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter book status: ");
        String status = scanner.nextLine();

        return bookManager.updateBookStatus(title, author, releaseYear, AssetStatus.valueOf(status));
    }

    private String getAllBooksCommand() {
        return bookManager.getAllBooks().toString();
    }

}
