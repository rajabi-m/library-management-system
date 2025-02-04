package org.example;

import org.example.model.Book;
import org.example.model.Library;
import org.example.model.LinkedList;

public class Program {
    // using target/ folder to store files, so they are ignored by git
    private static final String booksDatabaseFilePath = "target/books.txt";

    private static BooksDatabase booksDatabase;


    public static void main(String[] args) {
        initializeFields();

        var library = loadLibrary();
        CLIView cliView = new CLIView(library, System.in);
        cliView.run();

        saveLibrary(library);
    }

    private static void saveLibrary(Library library) {
        booksDatabase.writeBooksToFile(library.getBooks());
    }

    private static void initializeFields() {
        booksDatabase = new BooksDatabase(booksDatabaseFilePath);
    }

    private static Library loadLibrary() {
        Library library = new Library();
        LinkedList<Book> books = booksDatabase.readBooksFromFile();
        for (Book book : books) {
            library.addBook(book);
        }
        return library;
    }
}
