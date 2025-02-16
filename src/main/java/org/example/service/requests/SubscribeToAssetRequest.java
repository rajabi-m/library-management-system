package org.example.service.requests;

import org.example.model.observer.AssetSubscriber;

public class SubscribeToAssetRequest extends AssetIdRequest {
    private final AssetSubscriber subscriber;

    public SubscribeToAssetRequest(String assetId, AssetSubscriber subscriber) {
        super(assetId);
        this.subscriber = subscriber;
    }

    public AssetSubscriber getSubscriber() {
        return subscriber;
    }
}
