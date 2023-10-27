package org.fubar;

import java.util.ArrayList;
import java.util.List;

class MyMap<K, V> {
    private List<MyEntry<K, V>> entries;

    public MyMap() {
        entries = new ArrayList<>();
    }

    public void put(K key, V value) {
        for (MyEntry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        entries.add(new MyEntry<>(key, value));
    }

    public V getOrDefault(K key, V defaultValue) {
        for (MyEntry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return defaultValue;
    }
}

