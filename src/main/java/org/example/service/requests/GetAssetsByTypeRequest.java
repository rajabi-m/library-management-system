package org.example.service.requests;

public class GetAssetsByTypeRequest extends Request{
    private final String assetType;

    public GetAssetsByTypeRequest(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetType() {
        return assetType;
    }
}
