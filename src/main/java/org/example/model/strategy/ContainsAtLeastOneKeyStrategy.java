package org.example.model.strategy;

import org.example.model.data_structure.InvertedIndexMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContainsAtLeastOneKeyStrategy<T, R> implements InvertedIndexSearchStrategy<T, R> {
    @Override
    public List<R> preformSearch(InvertedIndexMap<T, R> invertedIndexMap, List<T> keys) {
        Set<R> output = new HashSet<>();
        for (var key : keys) {
            var newResults = invertedIndexMap.get(key);
            if (newResults == null) newResults = new ArrayList<>();

            output.addAll(newResults);
        }

        return new ArrayList<>(output);
    }
}
