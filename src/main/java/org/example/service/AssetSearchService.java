package org.example.service;

import org.example.model.Asset;
import org.example.model.data_structure.InvertedIndexMap;
import org.example.model.strategy.InvertedIndexSearchStrategy;

import java.util.ArrayList;
import java.util.List;

public class AssetSearchService {
    private final InvertedIndexMap<String, String> invertedIndexMap;

    // Constructor
    public AssetSearchService() {
        this.invertedIndexMap = new InvertedIndexMap<>();
    }

    // Methods
    public void indexAssetTitle(Asset asset) {
        var words = List.of(asset.getTitle().split(" "));

        words.forEach(word -> {
            if (word.isBlank()) return;
            word = word.toLowerCase();
            invertedIndexMap.add(word, asset.getId());
        });
    }

    public List<String> queryAssets(String query, InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy) {
        query = query.toLowerCase();
        ArrayList<String> words = splitWords(query);

        var output = new ArrayList<Asset>();

        return invertedIndexSearchStrategy.preformSearch(invertedIndexMap, words);
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
