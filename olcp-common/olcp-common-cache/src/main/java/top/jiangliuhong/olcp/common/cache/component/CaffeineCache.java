package top.jiangliuhong.olcp.common.cache.component;

import com.github.benmanes.caffeine.cache.Cache;
import top.jiangliuhong.olcp.common.cache.ICache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CaffeineCache<K, V> implements ICache<K, V> {

    private final Cache<K, V> cache;
    private final String name;

    private final Set<K> keys;

    public CaffeineCache(Cache<K, V> cache, String name) {
        this.cache = cache;
        this.name = name;
        this.keys = new HashSet<>();
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
        this.keys.add(k);
    }

    @Override
    public void put(Map<K, V> map) {
        this.cache.putAll(map);
        this.keys.addAll(map.keySet());
    }

    @Override
    public void remove(K k) {
        this.cache.invalidate(k);
        this.keys.remove(k);
    }

    @Override
    public void remove(Iterable<? extends K> ks) {
        this.cache.invalidateAll(ks);
        Set<K> kSet = new HashSet<>();
        ks.forEach(kSet::add);
        this.keys.removeAll(kSet);
    }

    @Override
    public boolean exist(K k) {
        //V v = this.cache.getIfPresent(k);
        return this.keys.contains(k);
    }

    @Override
    public Iterable<K> keys() {
        return this.keys;
    }
}
