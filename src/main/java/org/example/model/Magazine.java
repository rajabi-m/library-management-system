package org.example.model;
import java.time.LocalDate;
import java.util.Objects;

public class Magazine extends BorrowableAsset{
    private String publisher;
    private String releaseDate;

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

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
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

    @Override
    public void update(Asset asset) {
        if (!(asset instanceof Magazine magazine)) return;
        setTitle(magazine.getTitle());
        setPublisher(magazine.getPublisher());
        setReleaseDate(magazine.getReleaseDate());
    }
}
