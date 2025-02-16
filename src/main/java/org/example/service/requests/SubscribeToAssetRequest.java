package org.example.service.requests;

import org.example.model.observer.Subscriber;

public class SubscribeToAssetRequest extends AssetIdRequest {
    private final Subscriber subscriber;

    public SubscribeToAssetRequest(String assetId, Subscriber subscriber) {
        super(assetId);
        this.subscriber = subscriber;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
}
