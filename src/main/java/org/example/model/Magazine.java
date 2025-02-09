package org.example.model;

import java.util.Objects;

public class Magazine extends BorrowableAsset{
    private final String publisher;
    private final String releaseDate;

    public Magazine(String title, String publisher, String releaseDate, AssetStatus status) {
        this.title = title;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.status = status;
    }

    public Magazine(String title, String publisher, String releaseDate) {
        this.title = title;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.status = AssetStatus.Exist;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toCsv() {
        return title + "," + publisher + "," + releaseDate + "," + status;
    }

    public static Magazine fromCsv(String csv){
        String[] parts = csv.split(",");
        return new Magazine(parts[0], parts[1], parts[2], AssetStatus.valueOf(parts[3]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Magazine magazine)) return false;
        return Objects.equals(publisher, magazine.publisher) && Objects.equals(releaseDate, magazine.releaseDate)
                && Objects.equals(title, magazine.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publisher, releaseDate);
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", status=" + status +
                ", returnTime=" + returnTime +
                '}';
    }

    @Override
    public String display() {
        return "Magazine: '" + title + "'";
    }
}
