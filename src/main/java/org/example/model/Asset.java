package org.example.model;

public abstract class Asset {
    protected String title;
    protected AssetStatus status;

    public String getTitle() {
        return title;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public abstract String toCsv();

    public static Asset fromCsv(String csv){
        throw new UnsupportedOperationException("Not implemented.");
    }
}
