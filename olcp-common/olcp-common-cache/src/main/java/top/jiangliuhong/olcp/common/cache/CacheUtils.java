package top.jiangliuhong.olcp.common.cache;

import top.jiangliuhong.olcp.common.utils.SpringUtils;

import java.util.Map;
import java.util.Set;

public final class CacheUtils {

    private static final class ContextHolder {
        private static final CacheContext context = SpringUtils.getBean(CacheContext.class);
    }

    public static CacheContext getContext() {
        return ContextHolder.context;
    }

    public static <K, V> ICache<K, V> getCache(String cacheName) {
        return getContext().getCache(cacheName);
    }

    public static <K, V> V getCacheValue(String cacheName, K k) {
        ICache<K, V> cache = getCache(cacheName);
        return cache.get(k);
    }

    public static <K, V> void putCacheValue(String cacheName, K k, V v) {
        ICache<K, V> cache = getCache(cacheName);
        cache.put(k, v);
    }

    public static <K, V> void putCacheValue(String cacheName, Map<K, V> maps) {
        ICache<K, V> cache = getCache(cacheName);
        cache.put(maps);
    }

    public static <K> boolean exist(String cacheName, K k) {
        ICache<K, Object> cache = getCache(cacheName);
        return cache.exist(k);
    }

    public static <K> Set<K> keys(String cacheName) {
        ICache<K, Object> cache = getCache(cacheName);
        return cache.keys();
    }

    public static <K> void remove(String cacheName, K k) {
        ICache<K, Object> cache = getCache(cacheName);
        cache.remove(k);
    }
}
