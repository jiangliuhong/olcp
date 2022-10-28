package top.jiangliuhong.olcp.common.cache;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.exception.CacheNotFoundException;
import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;
import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

import java.util.HashMap;
import java.util.Map;

@Log
public class CacheContext {


    private final Map<String, ICache> cacheMap = new HashMap<>();
    private CacheProperties properties;
    private CacheFactory cacheFactory;

    public CacheContext(CacheProperties properties, CacheFactory cacheFactory) {
        this.properties = properties;
        this.cacheFactory = cacheFactory;
        this.factoryBuilder();
    }

    public <K, V> ICache<K, V> getCache(String name) {
        ICache iCache = cacheMap.get(name);
        if (iCache == null) {
            throw new CacheNotFoundException(name);
        }
        return iCache;
    }

    public ICacheFactory getFactory(String name) {
        return cacheFactory.getFactory(name);
    }

    private void factoryBuilder() {
        for (CacheInfo group : this.properties.getGroups()) {
            ICacheFactory factory = cacheFactory.getFactory(group.getName());
            ICache cache = factory.getCache(group);
            this.cacheMap.put(cache.name(), cache);
        }
    }

    public <K, V> ICache<K, V> addCache(CacheInfo info) {
        ICacheFactory factory = this.cacheFactory.getFactory(info.getType());
        if (factory == null) {
            throw new RuntimeException("");
        }
        ICache<K, V> cache = factory.getCache(info);
        this.addCache(cache);
        return cache;
    }

    public void addCache(ICache cache) {
        if (StringUtils.isBlank(cache.name())) {
            return;
        }
        for (CacheInfo group : this.properties.getGroups()) {
            if (StringUtils.equals(group.getName(), cache.name())) {
                log.warning("The cache name[" + cache.name() + "] is the same as the system keyword");
                return;
            }
        }
        if (this.cacheMap.containsKey(cache.name())) {
            log.warning("duplicate cache names :" + cache.name());
            return;
        }
        this.cacheMap.put(cache.name(), cache);
        log.info("Cache[" + cache.name() + "] added successfully");
    }
}
