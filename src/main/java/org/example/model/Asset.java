package org.example.model;

import java.time.LocalDate;

public abstract class Asset {
    protected String title;
    protected AssetStatus status;
    protected LocalDate returnTime;

    public String getTitle() {
        return title;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public LocalDate getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDate returnTime) {
        this.returnTime = returnTime;
    }

    public abstract String display();

    public abstract String toCsv();
}
