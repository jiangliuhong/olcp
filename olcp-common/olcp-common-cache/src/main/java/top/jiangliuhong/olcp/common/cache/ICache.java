package top.jiangliuhong.olcp.common.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface ICache<K, V> {

    public String name();

    public V get(K k);

    public Map<K, V> get(Iterable<? extends K> ks);

    public void put(K k, V v);

    public void put(Map<K, V> map);

    public void remove(K k);

    public void remove(Iterable<? extends K> ks);

    public boolean exist(K k);

    public Set<K> keys();

    Set<Entry<K, V>> entrySet();

    interface Entry<K, V> {
        K getKey();

        V getValue();
    }
}
