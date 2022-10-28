package top.jiangliuhong.olcp.common.cache;

import top.jiangliuhong.olcp.common.utils.SpringUtils;

import java.util.Map;

public final class CacheUtils {

    private static final class ContextHolder {
        private static final CacheContext context = SpringUtils.getBean(CacheContext.class);
    }

    public static CacheContext getContext() {
        return ContextHolder.context;
    }

    public static <K, V> ICache<K, V> getCache(String name) {
        return getContext().getCache(name);
    }

    public static <K, V> V getCacheValue(String name, K k) {
        ICache<K, V> cache = getCache(name);
        return cache.get(k);
    }

    public static <K, V> void putCacheValue(String name, K k, V v) {
        ICache<K, V> cache = getCache(name);
        cache.put(k, v);
    }

    public static <K, V> void putCacheValue(String name, Map<K, V> maps) {
        ICache<K, V> cache = getCache(name);
        cache.put(maps);
    }

    public static <K> boolean exist(String name, K k) {
        ICache<K, Object> cache = getCache(name);
        return cache.exist(k);
    }

}
