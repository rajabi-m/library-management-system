package org.example.service.requests;

public abstract class AssetIdRequest extends Request {
    private final String assetId;

    public AssetIdRequest(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetId() {
        return assetId;
    }
}
