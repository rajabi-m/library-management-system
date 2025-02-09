package org.example.model;

import org.example.utils.ParserUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Book extends BorrowableAsset {
    // Fields
    private final String author;
    private final int releaseYear;

    // Constructor
    public Book(String title, String author, int releaseYear, AssetStatus status, LocalDate returnDate) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.status = status;
        this.returnDate = returnDate;
    }

    public Book(String title, String author, int releaseYear) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
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
                ", returnTime=" + returnDate +
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

    @Override
    public String toCsv() {
        return title + "," + author + "," + releaseYear + "," + status + "," + returnDate;
    }

    @Override
    public String display() {
        return "Book: '" + title + "' from '" + author + "'";
    }

    public static Book fromCsv(String csv) {
        String[] data = csv.split(",");
        return new Book(data[0], data[1], Integer.parseInt(data[2]), AssetStatus.valueOf(data[3]), ParserUtils.parseDate(data[4]));
    }
}
