package org.example.model;

import org.example.model.dto.AssetDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {
    private final HashMap<String, Asset> assetsMap;
    private final InvertedIndexMap<String, String> invertedIndexMap;

    // Constructor
    public Library() {
        this.invertedIndexMap = new InvertedIndexMap<>();
        this.assetsMap = new HashMap<>();
    }

    public Library(ArrayList<Asset> assets) {
        this.assetsMap = new HashMap<>();
        this.invertedIndexMap = new InvertedIndexMap<>();

        for (var asset: assets) {
            this.addAsset(asset);
        }
    }

    // Methods
    public String addAsset(Asset asset) {
        if (assetsMap.containsValue(asset)) {
            return "Asset already exists in the library!";
        }

        assetsMap.put(asset.getId(), asset);

        String[] words = asset.getTitle().split(" ");
        for (String word : words) {
            if (word.isBlank()) continue;
            this.invertedIndexMap.add(word, asset.getId());
        }
        return "Asset added successfully!";
    }

    public String removeAssetById(String assetId) {
        if (!assetsMap.containsKey(assetId)) {
            return "Asset does not exist in the library!";
        }

        this.assetsMap.remove(assetId);
        return "Asset removed successfully!";
    }

    public ArrayList<AssetDTO> getAssetsByTitle(String title){
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : this.assetsMap.values()) {
            if (asset.getTitle().equals(title)) {
                output.add(new AssetDTO(asset.getId(), asset.toString()));
            }
        }
        return output;
    }

    public ArrayList<AssetDTO> getAssets() {
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : this.assetsMap.values()) {
            output.add(new AssetDTO(asset.getId(), asset.toString()));
        }
        return output;
    }

    public ArrayList<AssetDTO> getBorrowableAssets() {
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : this.assetsMap.values()) {
            if (!(asset instanceof BorrowableAsset borrowableAsset)) {
                continue;
            }
            output.add(new AssetDTO(borrowableAsset.getId(), borrowableAsset.toString()));
        }

        return output;
    }

    public ArrayList<AssetDTO> getAssetsByType(String type){
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : assetsMap.values()) {
            if (!asset.getClass().getSimpleName().equals(type)) continue;
            output.add(new AssetDTO(asset.getId(), asset.toString()));
        }
        return output;
    }

    public ArrayList<AssetDTO> queryAssets(String query) {
        String[] splitResult = query.split(" ");
        ArrayList<String> words = new ArrayList<>();
        for (String word : splitResult) {
            if (word.isBlank()) continue;
            words.add(word);
        }

        var output = new ArrayList<AssetDTO>();
        var queryResult = invertedIndexMap.query(words);
        for (String assetId : queryResult) {
            var asset = assetsMap.get(assetId);
            var assetDTO = new AssetDTO(asset.getId(), asset.toString());
            output.add(assetDTO);
        }
        return output;
    }

    public String borrowAssetById(String assetId) {
        var returnDate = LocalDate.now().plusDays(14);
        return borrowAssetById(assetId, returnDate);
    }

    public String borrowAssetById(String assetId, LocalDate returnDate) {
        if (!assetsMap.containsKey(assetId)) {
            return "Asset does not exist in the library!";
        }

        Asset asset = assetsMap.get(assetId);

        if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Exist){
            return "This asset is not available for borrowing";
        }

        borrowableAsset.setStatus(AssetStatus.Borrowed);
        borrowableAsset.setReturnDate(returnDate);
        return "Asset successfully borrowed";
    }

    public String returnAssetById(String assetId) {
        if (!assetsMap.containsKey(assetId)) {
            return "Asset does not exist in the library!";
        }

        Asset asset = assetsMap.get(assetId);
        if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Borrowed){
            return "This asset is not borrowed";
        }

        borrowableAsset.setStatus(AssetStatus.Exist);
        borrowableAsset.setReturnDate(null);
        return "Asset successfully brought back";
    }

    public List<Asset> getAllAssetObjects() {
        return new ArrayList<>(assetsMap.values());
    }
}
