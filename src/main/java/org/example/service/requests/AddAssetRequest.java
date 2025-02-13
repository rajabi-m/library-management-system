package org.example.service.requests;

import org.example.model.Asset;

public class AddAssetRequest extends Request {
    private final Asset asset;

    public AddAssetRequest(Asset asset){
        this.asset = asset;
    }

    public Asset getAsset(){
        return asset;
    }
}
