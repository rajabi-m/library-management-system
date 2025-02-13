package org.example.service.requests;

import java.time.LocalDate;

public class BorrowAssetRequest extends AssetIdRequest{
    private final LocalDate returnDate;

    public BorrowAssetRequest(String assetId, LocalDate returnDate) {
        super(assetId);
        this.returnDate = returnDate;
    }


    public LocalDate getReturnDate() {
        return returnDate;
    }
}
