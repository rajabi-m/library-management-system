package org.example.model;

public abstract class Asset {
    protected String title;

    public String getTitle() {
        return title;
    }

    public abstract String toCsv();

    public static Asset fromCsv(String csv){
        throw new UnsupportedOperationException("Not implemented.");
    }
}
