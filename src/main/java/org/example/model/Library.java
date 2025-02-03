package org.example.model;

import java.util.Comparator;

public class Library {
    private final LinkedList<Book> books;

    // Constructor
    public Library() {
        this.books = new LinkedList<>();
    }

    // Methods
    public void addBook(Book book) {
        for (Book b : this.books) {
            if (b.getTitle().equals(book.getTitle()) && b.getAuthor().equals(book.getAuthor())) {
                throw new RuntimeException("This author have a book with the same title already in the library.");
            }
        }
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public void updateBookStatus(Book book, BookStatus status) {
        book.setStatus(status);
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

    // Every book is unique by title and author
    public Book getBook(String title, String author){
        for (Book book : this.books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }

    public void sortBooksByReleaseYear() {
        this.books.sort(Comparator.comparingInt(Book::getReleaseYear));
    }

}
