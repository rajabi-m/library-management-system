package org.example.model;

public abstract class BorrowableAsset extends Asset{
    protected AssetStatus status;

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }
}
