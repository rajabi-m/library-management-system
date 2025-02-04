package org.example.model;

public abstract class Asset {
    protected String title;
    protected AssetStatus status;

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }
}
