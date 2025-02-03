package org.example.models;

import java.util.ArrayList;
import java.util.Comparator;

public class Library {
    private final ArrayList<Book> books;

    // Constructor
    public Library() {
        this.books = new ArrayList<>();
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

    public ArrayList<Book> getBooks() {
        return this.books;
    }

    public ArrayList<Book> getBooksByTitle(String title){
        ArrayList<Book> output = new ArrayList<>();
        for (Book book : this.books) {
            if (book.getTitle().equals(title)) {
                output.add(book);
            }
        }
        return output;
    }

    public ArrayList<Book> getBooksByAuthor(String author){
        ArrayList<Book> output = new ArrayList<>();
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
