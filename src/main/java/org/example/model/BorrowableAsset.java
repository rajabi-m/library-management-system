package org.example.model;

import java.time.LocalDate;

public abstract class BorrowableAsset extends Asset {
    private AssetStatus status;
    private LocalDate returnDate;

    public BorrowableAsset(String id, String title, AssetStatus status, LocalDate returnDate) {
        super(id, title);
        this.status = status;
        this.returnDate = returnDate;
    }

    public BorrowableAsset(String title){
        super(title);
        this.status = AssetStatus.Exist;
    }

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
