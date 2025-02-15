package org.example.model;

import java.util.Objects;

public class Thesis extends Asset{
    private String author;
    private String supervisor;
    private String department;
    private String publishDate;

    public Thesis(String id, String title, String author, String supervisor, String department, String publishDate) {
        super(id, title);
        this.publishDate = publishDate;
        this.author = author;
        this.supervisor = supervisor;
        this.department = department;
    }

    public Thesis(String title, String author, String supervisor, String department, String publishDate) {
        super(title);
        this.author = author;
        this.supervisor = supervisor;
        this.department = department;
        this.publishDate = publishDate;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thesis thesis)) return false;
        return Objects.equals(author, thesis.author) &&
                Objects.equals(supervisor, thesis.supervisor) &&
                Objects.equals(department, thesis.department) &&
                Objects.equals(publishDate, thesis.publishDate) &&
                Objects.equals(getTitle(), thesis.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), author, supervisor, department, publishDate);
    }

    @Override
    public String toString() {
        return "Thesis{" +
                "title='" + getTitle() + '\'' +
                ", author='" + author + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", department='" + department + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }

    @Override
    public String display() {
        return "Thesis: '" + getTitle() + "' from '" + author + "' to '" + supervisor + "'";
    }

    @Override
    public void update(Asset asset) {
        if (!(asset instanceof Thesis thesis)) return;
        setTitle(thesis.getTitle());
        setAuthor(thesis.getAuthor());
        setSupervisor(thesis.getSupervisor());
        setDepartment(thesis.getDepartment());
        setPublishDate(thesis.getPublishDate());
    }
}
