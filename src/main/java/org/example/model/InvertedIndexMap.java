package org.example.model;

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
}
