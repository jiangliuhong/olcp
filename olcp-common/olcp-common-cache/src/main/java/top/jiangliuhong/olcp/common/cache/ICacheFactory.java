package top.jiangliuhong.olcp.common.cache;

import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;

public interface ICacheFactory {

    public String name();

    public <K, V> ICache<K, V> getCache(CacheInfo info);
}
