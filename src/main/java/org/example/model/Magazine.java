package org.example.model;

import org.example.utils.ParserUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Magazine extends BorrowableAsset{
    private final String publisher;
    private final String releaseDate;

    public Magazine(String id, String title, String publisher, String releaseDate, AssetStatus status, LocalDate returnDate) {
        super(id, title, status, returnDate);
        this.publisher = publisher;
        this.releaseDate = releaseDate;
    }

    public Magazine(String title, String publisher, String releaseDate) {
        super(title);
        this.publisher = publisher;
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toCsv() {
        return getId() + "," + getTitle() + "," + publisher + "," + releaseDate + "," + getStatus() + "," + getReturnDate();
    }

    public static Magazine fromCsv(String csv){
        String[] parts = csv.split(",");
        return new Magazine(parts[0], parts[1], parts[2], parts[3], AssetStatus.valueOf(parts[4]), ParserUtils.parseDate(parts[5]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Magazine magazine)) return false;
        return Objects.equals(publisher, magazine.publisher) && Objects.equals(releaseDate, magazine.releaseDate)
                && Objects.equals(getTitle(), magazine.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), publisher, releaseDate);
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "title='" + getTitle() + '\'' +
                ", publisher='" + publisher + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", status=" + getStatus() +
                ", returnTime=" + getReturnDate() +
                '}';
    }

    @Override
    public String display() {
        return "Magazine: '" + getTitle() + "'";
    }
}
