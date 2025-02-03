package org.example.models;

public class Book {
    // Fields
    private String title;
    private String author;
    private int releaseYear;
    private BookStatus status;

    // Constructor
    public Book(String title, String author, int releaseYear, BookStatus status) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public BookStatus getStatus() {
        return status;
    }
}
