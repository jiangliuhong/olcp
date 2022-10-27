package top.jiangliuhong.olcp.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

import java.util.HashMap;
import java.util.Map;

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
        return cacheMap.get(name);
    }

    private void factoryBuilder() {
        for (CacheProperties.Group group : this.properties.getGroups()) {
            ICacheFactory factory = cacheFactory.getFactory(group.getName());
            ICache cache = factory.getCache(group);
            this.cacheMap.put(cache.name(), cache);
        }
    }
}
