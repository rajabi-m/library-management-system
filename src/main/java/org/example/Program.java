package org.example;

import org.example.model.*;

public class Program {
    // using target/ folder to store files, so they are ignored by git
    private static final String booksDatabaseFilePath = "target/books.txt";
    private static final String outputFilePath = "target/output.txt";

    private final static boolean debugMode = true;

    private static BooksDatabase booksDatabase;


    public static void main(String[] args) {
        initializeFields();

        var library = loadLibrary();
        var outputDisplay = debugMode ? new CLIOutputStream() : new FileDisplay(outputFilePath);
        MainMenuView mainMenuView = new MainMenuView(library, outputDisplay);
        mainMenuView.run();

        saveLibrary(library);
    }

    private static void saveLibrary(Library library) {
//        booksDatabase.writeBooksToFile(library.getAssets());
    }

    private static void initializeFields() {
        booksDatabase = new BooksDatabase(booksDatabaseFilePath);
    }

    private static Library loadLibrary() {
//        LinkedList<Book> books = booksDatabase.readBooksFromFile();
        return new Library();
    }
}
