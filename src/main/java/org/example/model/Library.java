package org.example.model;

import org.example.model.data_structure.InvertedIndexMap;
import org.example.model.strategy.InvertedIndexSearchStrategy;

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

    // Methods
    public void addAsset(Asset asset) {
        assetsMap.put(asset.getId(), asset);
        indexAssetTitle(asset);
    }

    public boolean containsAsset(String assetId) {
        return assetsMap.containsKey(assetId);
    }

    public boolean containsAsset(Asset asset) {
        return assetsMap.containsValue(asset);
    }

    public Asset getAssetById(String assetId) {
        return assetsMap.get(assetId);
    }

    public void indexAssetTitle(Asset asset) {
        var words = List.of(asset.getTitle().split(" "));

        words.forEach(word -> {
            if (word.isBlank()) return;
            word = word.toLowerCase();
            invertedIndexMap.add(word, asset.getId());
        });
    }

    public void removeAsset(Asset asset) {
        assetsMap.remove(asset.getId());
    }

    public void removeAsset(String assetId) {
        assetsMap.remove(assetId);
    }

    public List<Asset> getAllAssets() {
        return assetsMap.values().stream().toList();
    }

    public List<Asset> queryAssets(String query, InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy) {
        query = query.toLowerCase();
        ArrayList<String> words = splitWords(query);

        var output = new ArrayList<Asset>();
        List<String> queryResult = invertedIndexSearchStrategy.preformSearch(invertedIndexMap, words);

        for (String assetId : queryResult) {
            if (!assetsMap.containsKey(assetId)) continue;
            var asset = assetsMap.get(assetId);
            output.add(asset);
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
}
