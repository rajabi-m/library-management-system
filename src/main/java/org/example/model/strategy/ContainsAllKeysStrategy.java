package org.example.model.strategy;

import org.example.model.data_structure.InvertedIndexMap;

import java.util.ArrayList;
import java.util.List;

public class ContainsAllKeysStrategy<T, R> implements InvertedIndexSearchStrategy<T, R> {

    @Override
    public List<R> preformSearch(InvertedIndexMap<T, R> invertedIndexMap, List<T> keys) {
        boolean isFirst = true;
        ArrayList<R> output = null;
        for (var key : keys) {
            var newResults = invertedIndexMap.get(key);
            if (newResults == null) newResults = new ArrayList<>();

            if (isFirst) {
                output = new ArrayList<>(newResults);
                isFirst = false;
                continue;
            }

            output.retainAll(newResults);
        }

        output = output == null ? new ArrayList<>() : output;
        return output;
    }
}
