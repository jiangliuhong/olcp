package top.jiangliuhong.olcp.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringMap<V> implements Map<String, V> {

    private static final int DEFAULT_CAPACITY = 8;

    private Map<String, V> values;

    public StringMap() {
        init(DEFAULT_CAPACITY);
    }

    public StringMap(int initialCapacity) {
        init(initialCapacity);
    }

    public StringMap(Map<String, V> cloneMap) {
        init(cloneMap.size());
        putAll(cloneMap);
    }


    private void init(int capacity) {
        this.values = new HashMap<>(capacity);
    }


    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.values.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.values.get(key);
    }

    @Override
    public V put(String key, V value) {
        return this.values.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return this.values.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        this.values.putAll(m);
    }

    @Override
    public void clear() {
        this.values.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.values.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.values.values();
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return this.values.entrySet();
    }
}
