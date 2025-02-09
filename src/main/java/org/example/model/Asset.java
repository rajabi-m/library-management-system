package org.example.model;

import java.time.LocalDate;
import java.util.Date;

public abstract class Asset {
    protected String title;
    protected AssetStatus status;
    protected LocalDate lastUpdate;

    public String getTitle() {
        return title;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public abstract String display();

    public abstract String toCsv();

    public static Asset fromCsv(String csv){
        throw new UnsupportedOperationException("Not implemented.");
    }
}
