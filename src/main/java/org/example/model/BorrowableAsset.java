package org.example.model;

import java.time.LocalDate;

public abstract class BorrowableAsset extends Asset {
    protected AssetStatus status;
    protected LocalDate returnTime;

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

}
