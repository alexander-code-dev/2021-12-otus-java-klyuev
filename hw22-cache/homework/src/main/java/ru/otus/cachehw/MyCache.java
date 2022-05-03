package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(MyCache.class);
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
        this.executeListener(key, value, "put");
    }

    @Override
    public void remove(K key) {
        this.executeListener(key, this.cache.get(key), "remove");
        this.cache.remove(key);
    }

    @Override
    public V get(K key) {
        this.executeListener(key, this.cache.get(key), "get");
        return this.cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void executeListener(K key, V value, String action) {
        try {
            for (HwListener<K, V> listener:listeners) {
                listener.notify(key, value, action);
            }
        } catch (RuntimeException e) {
            log.warn("Runtime error listener, {}", e.getMessage(), e);
        }
    }
}
