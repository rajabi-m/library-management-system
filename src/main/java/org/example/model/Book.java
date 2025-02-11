package org.example.model;


import java.time.LocalDate;
import java.util.Objects;

public class Book extends BorrowableAsset {
    // Fields
    private final String author;
    private final int releaseYear;

    // Constructor
    public Book(String id, String title, String author, int releaseYear, AssetStatus status, LocalDate returnDate) {
        super(id, title, status, returnDate);
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public Book(String title, String author, int releaseYear) {
        super(title);
        this.author = author;
        this.releaseYear = releaseYear;
    }

    // Methods
    public String getAuthor() {
        return author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + getTitle() + '\'' +
                ", author='" + author + '\'' +
                ", releaseYear=" + releaseYear +
                ", status=" + getStatus() +
                ", returnTime=" + getReturnDate() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return releaseYear == book.releaseYear && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), author, releaseYear);
    }


    @Override
    public String display() {
        return "Book: '" + getTitle() + "' from '" + author + "'";
    }
}
