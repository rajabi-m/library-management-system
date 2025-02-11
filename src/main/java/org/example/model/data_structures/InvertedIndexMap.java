package org.example.model.data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndexMap<T, R> {
    private final Map<T, List<R>> map;

    public InvertedIndexMap() {
        this.map = new HashMap<>();
    }

    public void add(T key, R value) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(value);
    }

    public List<R> get(T key) {
        return map.get(key);
    }

    public boolean containsKey(T key) {
        return map.containsKey(key);
    }

    public List<R> query(List<T> keys) {
        boolean isFirst = true;
        ArrayList<R> output = null;
        for (var key : keys) {
            var newResults = get(key);

            if (isFirst) {
                if (newResults == null) return new ArrayList<>();
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
