package org.example.model;

import java.util.Objects;

public class Book extends Asset {
    // Fields
    private final String author;
    private final int releaseYear;

    // Constructor
    public Book(String title, String author, int releaseYear, AssetStatus status) {
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
