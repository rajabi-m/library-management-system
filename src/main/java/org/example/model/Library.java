package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private final ArrayList<Asset> assets;
    private final InvertedIndexMap<String, Asset> invertedIndexMap;

    // Constructor
    public Library() {
        this.invertedIndexMap = new InvertedIndexMap<>();
        this.assets = new ArrayList<>();
    }

    public Library(ArrayList<Asset> assets) {
        this.assets = new ArrayList<>();
        this.invertedIndexMap = new InvertedIndexMap<>();

        for (var asset: assets) {
            this.addAsset(asset);
        }
    }

    // Methods
    public String addAsset(Asset asset) {
        if (assets.contains(asset)) {
            return "Asset already exists in the library!";
        }

        this.assets.add(asset);

        String[] words = asset.getTitle().split(" ");
        for (String word : words) {
            if (word.isBlank()) continue;
            this.invertedIndexMap.add(word, asset);
        }
        return "Asset added successfully!";
    }

    public String removeAsset(Asset asset) {
        if (!this.assets.contains(asset)) {
            return "Asset does not exist in the library!";
        }

        this.assets.remove(asset);
        return "Asset removed successfully!";
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

    public ArrayList<Asset> getAssets() {
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

    public List<Asset> queryAssets(String query) {
        String[] splitResult = query.split(" ");
        ArrayList<String> words = new ArrayList<>();
        for (String word : splitResult) {
            if (word.isBlank()) continue;
            words.add(word);
        }

        return invertedIndexMap.query(words);
    }

    public String borrowAsset(Asset asset) {
        if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Exist){
            return "This asset is not available for borrowing";
        }

        borrowableAsset.setStatus(AssetStatus.Borrowed);
        var returnTime = LocalDate.now();
        returnTime = returnTime.plusDays(14);
        borrowableAsset.setReturnDate(returnTime);
        return "Asset successfully borrowed";
    }

    public String returnAsset(Asset asset) {
        if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Borrowed){
            return "This asset is not borrowed";
        }

        borrowableAsset.setStatus(AssetStatus.Exist);
        borrowableAsset.setReturnDate(null);
        return "Asset successfully brought back";
    }
}
