package org.example.model.strategy;

import org.example.model.data_structure.InvertedIndexMap;

import java.util.List;

public interface InvertedIndexSearchStrategy<T, R> {
    List<R> preformSearch(InvertedIndexMap<T, R> invertedIndexMap, List<T> keys);
}
