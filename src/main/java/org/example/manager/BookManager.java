package org.example.manager;

import org.example.model.*;

public class BookManager {
    private final Library library;

    public BookManager(Library library) {
        this.library = library;
    }

    private Book findBook(String title, String author, int releaseYear) {
        for (Asset asset : library.getAssets()) {
            if (!(asset instanceof Book book)) {
                continue;
            }

            if (book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getReleaseYear() == releaseYear) {
                return book;
            }
        }
        return null;
    }

    public String removeBook(String title, String author, int releaseYear) {
        var book = findBook(title, author, releaseYear);
        if (book == null) {
            return "Book does not exist in the library!";
        }
        library.removeAsset(book);
        return "Book removed successfully!";
    }

    public String addBook(String title, String author, int releaseYear) {
        var book = new Book(title, author, releaseYear, AssetStatus.Exist);
        if (library.getAssets().contains(book)) {
            return "Book already exists in the library!";
        }
        library.addAsset(book);
        return "Book added successfully!";
    }

    public String updateBookStatus(String title, String author, int releaseYear, AssetStatus status) {
        var book = findBook(title, author, releaseYear);
        if (book == null) {
            return "Book does not exist in the library!";
        }
        library.updateAssetStatus(book, status);
        return "The book status was successfully updated!";
    }

    public LinkedList<Book> getAllBooks() {
        var output = new LinkedList<Book>();
        for (Asset asset : library.getAssets()) {
            if (asset instanceof Book book) {
                output.add(book);
            }
        }
        return output;
    }
}
