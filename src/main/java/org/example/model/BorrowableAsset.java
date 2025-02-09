package org.example.model;

import java.time.LocalDate;

public abstract class BorrowableAsset extends Asset {
    protected AssetStatus status;
    protected LocalDate returnDate;

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
