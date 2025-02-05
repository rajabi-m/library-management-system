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
    public String addAsset(Asset asset) {
        if (assets.contains(asset)) {
            return "Asset already exists in the library!";
        }

        this.assets.add(asset);
        return "Asset added successfully!";
    }

    public String removeAsset(Asset asset) {
        if (!this.assets.contains(asset)) {
            return "Asset does not exist in the library!";
        }

        this.assets.remove(asset);
        return "Asset removed successfully!";
    }

    public String updateAssetStatus(BorrowableAsset asset, AssetStatus status) {
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

    public ArrayList<BorrowableAsset> getBorrowableAssetsByTitle(String title){
        var output = new ArrayList<BorrowableAsset>();
        for (Asset asset : this.assets) {
            if (asset.getTitle().equals(title) && asset instanceof BorrowableAsset borrowableAsset) {
                output.add(borrowableAsset);
            }
        }
        return output;
    }

    public LinkedList<Asset> getAssets() {
        return assets;
    }

    public ArrayList<Asset> getAssetsByType(String type){
        var output = new ArrayList<Asset>();
        for (Asset asset : this.assets) {
            if (!asset.getClass().getSimpleName().equals(type)) continue;
            output.add(asset);
        }
        return output;
    }
}
