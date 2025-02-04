package org.example;

import org.example.model.Book;
import org.example.model.FileDisplay;
import org.example.model.Library;
import org.example.model.LinkedList;

public class Program {
    // using target/ folder to store files, so they are ignored by git
    private static final String booksDatabaseFilePath = "target/books.txt";
    private static final String outputFilePath = "target/output.txt";

    private static BooksDatabase booksDatabase;


    public static void main(String[] args) {
        initializeFields();

        var library = loadLibrary();
        MainMenuView mainMenuView = new MainMenuView(library, new FileDisplay(outputFilePath));
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
