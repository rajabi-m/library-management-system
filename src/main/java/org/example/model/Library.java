package org.example.model;

import java.util.Comparator;

public class Library {
    private final LinkedList<Book> books;

    // Constructor
    public Library() {
        this.books = new LinkedList<>();
    }

    public Library(LinkedList<Book> books) {
        this.books = books;
    }

    // Methods
    public String addBook(Book book) {
        if (findBook(book.getTitle(), book.getAuthor(), book.getReleaseYear()) != null) {
            return "This book already exists in the library!";
        }

        this.books.add(book);
        return "The book was successfully added to the library!";
    }

    public String removeBook(String title, String author, int releaseYear) {
        Book book = findBook(title, author, releaseYear);
        if (book == null) {
            return "The book was not found in the library!";
        }

        this.books.remove(book);
        return "The book was successfully removed from the library!";
    }

    public String updateBookStatus(String title, String author, int releaseYear, BookStatus status) {
        Book book = findBook(title, author, releaseYear);
        if (book == null) {
            return "The book was not found in the library!";
        }

        book.setStatus(status);
        return "The book status was successfully updated!";
    }

    public LinkedList<Book> getBooks() {
        return this.books;
    }

    public LinkedList<Book> getBooksByTitle(String title){
        var output = new LinkedList<Book>();
        for (Book book : this.books) {
            if (book.getTitle().equals(title)) {
                output.add(book);
            }
        }
        return output;
    }

    public LinkedList<Book> getBooksByAuthor(String author){
        var output = new LinkedList<Book>();
        for (Book book : this.books) {
            if (book.getAuthor().equals(author)) {
                output.add(book);
            }
        }
        return output;
    }

    // Every book is unique by title and author & release year
    public Book findBook(String title, String author, int releaseYear){
        for (Book book : this.books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getReleaseYear() == releaseYear) {
                return book;
            }
        }
        return null;
    }

    public void sortBooksByReleaseYear() {
        this.books.sort(Comparator.comparingInt(Book::getReleaseYear));
    }

}
