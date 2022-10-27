package top.jiangliuhong.olcp.common.cache.component;

import com.github.benmanes.caffeine.cache.Cache;
import top.jiangliuhong.olcp.common.cache.ICache;

import java.util.Map;

public class CaffeineCache<K, V> implements ICache<K, V> {

    private final Cache<K, V> cache;
    private final String name;

    public CaffeineCache(Cache<K, V> cache, String name) {
        this.cache = cache;
        this.name = name;
    }

    public String name() {
        return this.name;
    }


    @Override
    public V get(K k) {
        return this.cache.getIfPresent(k);
    }

    @Override
    public Map<K, V> get(Iterable<? extends K> ks) {
        return this.cache.getAllPresent(ks);
    }

    @Override
    public void put(K k, V v) {
        this.cache.put(k, v);
    }

    @Override
    public void put(Map<K, V> map) {
        this.cache.putAll(map);
    }

    @Override
    public void remove(K k) {
        this.cache.invalidate(k);
    }

    @Override
    public void remove(Iterable<? extends K> ks) {
        this.cache.invalidateAll(ks);
    }

    @Override
    public boolean exist(K k) {
        V v = this.cache.getIfPresent(k);
        return v != null;
    }
}
