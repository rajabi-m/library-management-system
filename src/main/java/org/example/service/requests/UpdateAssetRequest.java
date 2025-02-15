package org.example.service.requests;

import org.example.model.Asset;

public class UpdateAssetRequest extends AssetIdRequest{
    private final Asset updatedAsset;

    public UpdateAssetRequest(String assetId, Asset updatedAsset) {
        super(assetId);
        this.updatedAsset = updatedAsset;
    }

    public Asset getUpdatedAsset() {
        return updatedAsset;
    }
}
