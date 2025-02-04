package org.example.model;

import java.util.Objects;

public class Book {
    // Fields
    private final String title;
    private final String author;
    private final int releaseYear;
    private BookStatus status;

    // Constructor
    public Book(String title, String author, int releaseYear, BookStatus status) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    // Methods
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

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseYear=" + releaseYear +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return releaseYear == book.releaseYear && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, releaseYear);
    }
}
