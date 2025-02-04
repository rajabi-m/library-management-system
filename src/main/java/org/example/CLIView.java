package org.example;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Library;
import org.example.model.LinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CLIView {
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Add a book", "Add a book to the library", this::addBookCommand),
            new CommandTemplate("Remove a book", "Remove a book from the library", this::removeBookCommand),
            new CommandTemplate("Update book status", "Update the status of a book", this::updateBookStatusCommand),
            new CommandTemplate("Get all books", "Get all books in the library", this::getAllBooksCommand),
            new CommandTemplate("Get books by title", "Get all books with a specific title", this::getBooksByTitleCommand),
            new CommandTemplate("Get books by author", "Get all books by a specific author", this::getBooksByAuthorCommand),
            new CommandTemplate("Sort books by release year", "Sort all books by release year", this::sortBooksByReleaseYearCommand),
            new CommandTemplate("Exit", "Exit the program", this::exitCommand),
    };
    private static final String outputFilePath = "target/output.txt";

    private final Library library;
    private final Scanner scanner;

    private boolean isProgramRunning = true;

    CLIView(Library library, InputStream inputStream){
        this.library = library;
        this.scanner = new Scanner(inputStream);
    }

    public void run() {
        printCommandList();
        while (isProgramRunning){
            System.out.print("> ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            handleMenuChoice(choice);
        }
    }

    private void handleMenuChoice(int choice){
        if (choice < 1 || choice > commands.length){
            System.out.println("Invalid choice!");
            return;
        }

        try {
            String result = commands[choice - 1].function.call();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
            bufferedWriter.write("Operation: " + commands[choice - 1].commandName + "\n");
            bufferedWriter.write("Result:\n");
            bufferedWriter.write(result);
            bufferedWriter.close();

        } catch (Exception exception){
            System.out.println("An error occurred: " + exception.getMessage());
        }
    }

    private String exitCommand() {
        isProgramRunning = false;
        return "Exiting the program!";
    }

    private String sortBooksByReleaseYearCommand() {
        library.sortBooksByReleaseYear();
        return "Books sorted by release year!";
    }

    private String getBooksByAuthorCommand() {
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();

        LinkedList<Book> books = library.getBooksByAuthor(author);

        return books.toString();
    }

    private String getBooksByTitleCommand() {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();

        LinkedList<Book> books = library.getBooksByTitle(title);

        return books.toString();
    }

    private String getAllBooksCommand() {
        return library.getBooks().toString();
    }


    private String updateBookStatusCommand() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book release year: ");
        int releaseYear = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter book status (Exist, Borrowed, Banned): ");
        BookStatus status = BookStatus.valueOf(scanner.nextLine());


        return library.updateBookStatus(title, author, releaseYear, status);
    }

    private String removeBookCommand() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.println("Enter book release year: ");
        int releaseYear = scanner.nextInt();

        return library.removeBook(title, author, releaseYear);
    }

    private String addBookCommand() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book release year: ");
        int releaseYear = scanner.nextInt();

        Book newBook = new Book(title, author, releaseYear, BookStatus.Exist);

        return library.addBook(newBook);
    }

    private void printCommandList(){
        System.out.print(welcomeText);
        for (int i = 0; i < commands.length; i++) {
            System.out.println("    " + (i + 1) + ". " + commands[i].commandName + " - " + commands[i].commandDescription);
        }
    }


    private record CommandTemplate(String commandName, String commandDescription, Callable<String> function) {}
}