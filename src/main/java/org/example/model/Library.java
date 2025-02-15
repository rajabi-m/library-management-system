package org.example.model;

import org.example.model.data_structure.InvertedIndexMap;
import org.example.model.dto.AssetDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Library {
    private final ConcurrentHashMap<String, Asset> assetsMap;
    private final InvertedIndexMap<String, String> invertedIndexMap;

    // Constructor
    public Library() {
        this.invertedIndexMap = new InvertedIndexMap<>();
        this.assetsMap = new ConcurrentHashMap<>();
    }

    public Library(List<Asset> assets) {
        this.assetsMap = new ConcurrentHashMap<>();
        this.invertedIndexMap = new InvertedIndexMap<>();

        for (var asset: assets) {
            this.addAsset(asset);
        }
    }

    // Methods
    public String addAsset(Asset asset) {
        synchronized (assetsMap) {
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
    }

    public String removeAssetById(String assetId) {
        synchronized (assetsMap) {
            if (!assetsMap.containsKey(assetId)) {
                return "Asset does not exist in the library!";
            }

            this.assetsMap.remove(assetId);
            return "Asset removed successfully!";
        }
    }

    public ArrayList<AssetDTO> getAssetsByTitle(String title){
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : this.assetsMap.values()) {
            if (asset.getTitle().equals(title)) {
                output.add(AssetDTO.of(asset));
            }
        }
        return output;
    }

    public ArrayList<AssetDTO> getAllAssets() {
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : this.assetsMap.values()) {
            output.add(AssetDTO.of(asset));
        }
        return output;
    }

    public ArrayList<AssetDTO> getAllBorrowableAssets() {
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : this.assetsMap.values()) {
            if (!(asset instanceof BorrowableAsset borrowableAsset)) {
                continue;
            }
            output.add(AssetDTO.of(borrowableAsset));
        }

        return output;
    }

    public ArrayList<AssetDTO> getAssetsByType(String type){
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : assetsMap.values()) {
            if (!asset.getClass().getSimpleName().equals(type)) continue;
            output.add(AssetDTO.of(asset));
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
            if (!assetsMap.containsKey(assetId)) continue;
            var asset = assetsMap.get(assetId);
            var assetDTO = AssetDTO.of(asset);
            output.add(assetDTO);
        }
        return output;
    }

    public String borrowAssetById(String assetId, LocalDate returnDate) {
        synchronized (assetsMap){
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
    }

    public String returnAssetById(String assetId) {
        synchronized (assetsMap) {
            if (!assetsMap.containsKey(assetId)) {
                return "Asset does not exist in the library!";
            }

            Asset asset = assetsMap.get(assetId);
            if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Borrowed){
                return "This asset is not borrowed";
            }

            borrowableAsset.setStatus(AssetStatus.Exist);
            borrowableAsset.setReturnDate(BorrowableAsset.defaultReturnDate);
            return "Asset successfully brought back";
        }
    }

    public AssetDTO getAssetById(String assetId) {
        synchronized (assetsMap) {
            if (!assetsMap.containsKey(assetId)) {
                return null;
            }

            Asset asset = assetsMap.get(assetId);
            return AssetDTO.of(asset);
        }
    }

    public List<Asset> getAllAssetObjects() {
        return new ArrayList<>(assetsMap.values());
    }

    public String updateAssetByAssetId(String assetId, Asset updatedAsset) {
        synchronized (assetsMap) {
            if (!assetsMap.containsKey(assetId)) {
                return "Asset does not exist in the library!";
            }
            Asset currentAsset = assetsMap.get(assetId);

            if (currentAsset.getClass() != updatedAsset.getClass()) {
                return "Asset types do not match";
            }

            if (currentAsset.equals(updatedAsset)) {
                return "No changes detected";
            }

            currentAsset.update(updatedAsset);
            return "Asset updated successfully";
        }
    }
}
