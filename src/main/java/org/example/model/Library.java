package org.example.model;

import java.util.ArrayList;

public class Library {
    private final LinkedList<Asset> assets;

    // Constructor
    public Library() {
        this.assets = new LinkedList<>();
    }

    public Library(LinkedList<Asset> assets) {
        this.assets = assets;
    }

    // Methods
    public void addAsset(Asset asset) {
        if (assets.contains(asset)) {
            return;
        }

        this.assets.add(asset);
    }

    public void removeAsset(Asset asset) {
        this.assets.remove(asset);
    }

    public String updateAssetStatus(Asset asset, AssetStatus status) {
        asset.setStatus(status);
        return "The asset status was successfully updated!";
    }

    public ArrayList<Asset> getAssetsByTitle(String title){
        var output = new ArrayList<Asset>();
        for (Asset asset : this.assets) {
            if (asset.getTitle().equals(title)) {
                output.add(asset);
            }
        }
        return output;
    }

    public LinkedList<Asset> getAssets() {
        return assets;
    }
}
