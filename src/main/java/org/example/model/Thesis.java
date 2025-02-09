package org.example.model;

import java.util.Objects;

public class Thesis extends Asset{
    private final String author;
    private final String supervisor;
    private final String department;
    private final String publishDate;

    public Thesis(String title, String author, String supervisor, String department, String publishDate) {
        this.publishDate = publishDate;
        this.title = title;
        this.author = author;
        this.supervisor = supervisor;
        this.department = department;
    }

    public String getAuthor() {
        return author;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getDepartment() {
        return department;
    }

    public String getPublishDate() {
        return publishDate;
    }

    @Override
    public String toCsv() {
        return title + "," + author + "," + supervisor + "," + department + "," + publishDate;
    }

    public static Thesis fromCsv(String csv) {
        var parts = csv.split(",");
        return new Thesis(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thesis thesis)) return false;
        return Objects.equals(author, thesis.author) &&
                Objects.equals(supervisor, thesis.supervisor) &&
                Objects.equals(department, thesis.department) &&
                Objects.equals(publishDate, thesis.publishDate) &&
                Objects.equals(title, thesis.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, supervisor, department, publishDate);
    }

    @Override
    public String toString() {
        return "Thesis{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", department='" + department + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }

    @Override
    public String display() {
        return "Thesis: '" + title + "' from '" + author + "' to '" + supervisor + "'";
    }
}
