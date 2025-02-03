package org.example.models;

import java.util.Comparator;

public class Library {
    private final LinkedList<Book> books;

    // Constructor
    public Library() {
        this.books = new LinkedList<>();
    }

    // Methods
    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public void updateBookStatus(Book book, BookStatus status) {
        book.setStatus(status);
    }

    public void printBooks() {
        for (Book book : this.books) {
            System.out.println(book);
        }
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

    public void sortBooksByReleaseYear() {
        this.books.sort(Comparator.comparingInt(Book::getReleaseYear));
    }




}
