package top.jiangliuhong.olcp.common.cache;

import java.util.List;
import java.util.Map;

public interface ICache<K, V> {

    public String name();

    public V get(K k);

    public Map<K, V> get(Iterable<? extends K> ks);

    public void put(K k, V v);

    public void put(Map<K, V> map);

    public void remove(K k);

    public void remove(Iterable<? extends K> ks);

    public boolean exist(K k);
}
