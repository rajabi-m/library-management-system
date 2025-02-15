package org.example.model.data_structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvertedIndexMap<T, R> {
    private final Map<T, List<R>> map;

    public InvertedIndexMap() {
        this.map = new ConcurrentHashMap<>();
    }

    public void add(T key, R value) {
        if (!map.containsKey(key)) {
            map.put(key, new CopyOnWriteArrayList<>());
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
