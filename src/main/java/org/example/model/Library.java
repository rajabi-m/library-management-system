package org.example.model;

import org.example.model.data_structure.InvertedIndexMap;
import org.example.model.dto.AssetDTO;
import org.example.model.observer.Subscribable;
import org.example.model.observer.Subscriber;
import org.example.model.strategy.ContainsAtLeastOneKeyStrategy;
import org.example.model.strategy.InvertedIndexSearchStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Library {
    private final ConcurrentHashMap<String, Asset> assetsMap;
    private final InvertedIndexMap<String, String> invertedIndexMap;

    private InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy = new ContainsAtLeastOneKeyStrategy<>();

    // Constructor
    private Library() {
        this.invertedIndexMap = new InvertedIndexMap<>();
        this.assetsMap = new ConcurrentHashMap<>();
    }

    // Methods
    public String addAsset(Asset asset) {
        synchronized (assetsMap) {
            if (assetsMap.containsValue(asset)) {
                return "Asset already exists in the library!";
            }

            assetsMap.put(asset.getId(), asset);

            indexAssetTitle(asset);
            return "Asset added successfully!";
        }
    }

    private void indexAssetTitle(Asset asset) {
        var words = List.of(asset.getTitle().split(" "));

        words.forEach(word -> {
            if (word.isBlank()) return;
            word = word.toLowerCase();
            invertedIndexMap.add(word, asset.getId());
        });
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

    public ArrayList<AssetDTO> getAssetsByTitle(String title) {
        var output = assetsMap.values().parallelStream()
                .filter(asset -> asset.getTitle().equals(title))
                .map(AssetDTO::of)
                .toList();
        return new ArrayList<>(output);
    }

    public ArrayList<AssetDTO> getAllAssets() {
        var allAssetDTOS = assetsMap.values().stream().map(AssetDTO::of).toList();
        return new ArrayList<>(allAssetDTOS);
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

    public ArrayList<AssetDTO> getAssetsByType(String type) {
        var output = new ArrayList<AssetDTO>();
        for (Asset asset : assetsMap.values()) {
            if (!asset.getClass().getSimpleName().equals(type)) continue;
            output.add(AssetDTO.of(asset));
        }
        return output;
    }

    public ArrayList<AssetDTO> queryAssets(String query) {
        query = query.toLowerCase();
        ArrayList<String> words = splitWords(query);

        var output = new ArrayList<AssetDTO>();
        var queryResult = invertedIndexSearchStrategy.preformSearch(invertedIndexMap, words);
        for (String assetId : queryResult) {
            if (!assetsMap.containsKey(assetId)) continue;
            var asset = assetsMap.get(assetId);
            var assetDTO = AssetDTO.of(asset);
            output.add(assetDTO);
        }
        return output;
    }

    private static ArrayList<String> splitWords(String query) {
        String[] splitResult = query.split(" ");
        ArrayList<String> words = new ArrayList<>();
        for (String word : splitResult) {
            if (word.isBlank()) continue;
            words.add(word);
        }
        return words;
    }

    public String borrowAssetById(String assetId, LocalDate returnDate) {
        synchronized (assetsMap) {
            if (!assetsMap.containsKey(assetId)) {
                return "Asset does not exist in the library!";
            }

            Asset asset = assetsMap.get(assetId);

            if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Exist) {
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
            if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Borrowed) {
                return "This asset is not borrowed";
            }

            borrowableAsset.notifySubscribers(asset.getTitle() + " is now available for borrowing");

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

            boolean titleChanged = !currentAsset.getTitle().equals(updatedAsset.getTitle());

            currentAsset.update(updatedAsset);

            if (titleChanged) {
                indexAssetTitle(currentAsset);
            }

            return "Asset updated successfully";
        }
    }

    private static final class InstanceHolder {
        private static final Library instance = new Library();
    }

    public static Library getInstance() {
        return InstanceHolder.instance;
    }

    public void addAllAssets(List<Asset> assets) {
        for (Asset asset : assets) {
            addAsset(asset);
        }
    }

    public void setInvertedIndexSearchStrategy(InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy) {
        this.invertedIndexSearchStrategy = invertedIndexSearchStrategy;
    }

    public String subscribeToAsset(String assetId, Subscriber subscriber) {
        synchronized (assetsMap) {
            if (!assetsMap.containsKey(assetId)) {
                return "Asset does not exist in the library!";
            }
            Asset asset = assetsMap.get(assetId);

            if (!(asset instanceof Subscribable subscribable)) {
                return "This asset is not subscribable";
            }

            subscribable.subscribe(subscriber);
            return "Subscribed successfully!";
        }

    }
}
