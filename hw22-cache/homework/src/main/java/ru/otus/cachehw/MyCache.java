package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MyCache.class);
    private final Map<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
        log.info("put key - {} from cache", key);
    }

    @Override
    public void remove(K key) {
        log.info("remove key - {} from cache", key);
        this.cache.remove(key);
    }

    @Override
    public V get(K key) {
        log.info("get key - {} from cache", key);
        return this.cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {

    }

    @Override
    public void removeListener(HwListener<K, V> listener) {

    }
}
