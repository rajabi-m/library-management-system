package org.example;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Library;
import org.example.model.LinkedList;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Program {
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private static final CommandTemplate[] commands = {
            new CommandTemplate("Add a book", "Add a book to the library", Program::addBookCommand),
            new CommandTemplate("Remove a book", "Remove a book from the library", Program::removeBookCommand),
            new CommandTemplate("Update book status", "Update the status of a book", Program::updateBookStatusCommand),
            new CommandTemplate("Get all books", "Get all books in the library", Program::getAllBooksCommand),
            new CommandTemplate("Get books by title", "Get all books with a specific title", Program::getBooksByTitleCommand),
            new CommandTemplate("Get books by author", "Get all books by a specific author", Program::getBooksByAuthorCommand),
            new CommandTemplate("Sort books by release year", "Sort all books by release year", Program::sortBooksByReleaseYearCommand),
    };

    private static final String booksFile = "books.txt";
    private static final String outputFile = "output.txt";

    private static final Library library = new Library();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Utils.readBooksFromFileAndAddToLibrary(booksFile, library);

        printCommandList();
        boolean success = false;
        while (!success){
            System.out.print("> ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            success = handleMenuChoice(choice);
        }

        Utils.writeLibraryBooksToFile(library, booksFile);
    }

    private static boolean handleMenuChoice(int choice){
        if (choice < 1 || choice > commands.length){
            System.out.println("Invalid choice!");
            return false;
        }

        try {
            String result = commands[choice - 1].function.call();
            System.out.println(result);
        } catch (Exception exception){
            System.out.println("An error occurred: " + exception.getMessage());
        }

        return true;
    }

    private static String sortBooksByReleaseYearCommand() {
        library.sortBooksByReleaseYear();
        return "Books sorted by release year!";
    }

    private static String getBooksByAuthorCommand() {
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();

        LinkedList<Book> books = library.getBooksByAuthor(author);

        return Utils.convertLinkedListToHumanReadableString(books);
    }

    private static String getBooksByTitleCommand() {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();

        LinkedList<Book> books = library.getBooksByTitle(title);

        return Utils.convertLinkedListToHumanReadableString(books);
    }

    private static String getAllBooksCommand() {
        return Utils.convertLinkedListToHumanReadableString(library.getBooks());
    }


    private static String updateBookStatusCommand() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book status (Exist, Borrowed, Banned): ");
        BookStatus status = BookStatus.valueOf(scanner.nextLine());

        for (Book book : library.getBooks()) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                library.updateBookStatus(book, status);
                return "Book '"+ title +"' of '"+ author +"' status updated to '"+ status +"'!";
            }
        }

        return "Book '"+ title +"' of '"+ author +"' not found!";
    }

    private static String removeBookCommand() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        for (Book book : library.getBooks()) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                library.removeBook(book);
                return "Book '"+ title +"' of '"+ author +"' removed successfully!";
            }
        }

        return "Book '"+ title +"' of '"+ author +"' not found!";
    }

    private static String addBookCommand() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book release year: ");
        int releaseYear = scanner.nextInt();

        Book newBook = new Book(title, author, releaseYear, BookStatus.Exist);
        library.addBook(newBook);

        return "Book '"+ title +"' added successfully!";
    }

    private static void printCommandList(){
        System.out.print(welcomeText);
        for (int i = 0; i < commands.length; i++) {
            System.out.println("    " + (i + 1) + ". " + commands[i].commandName + " - " + commands[i].commandDescription);
        }
    }

    private record CommandTemplate(String commandName, String commandDescription, Callable<String> function) {}
}