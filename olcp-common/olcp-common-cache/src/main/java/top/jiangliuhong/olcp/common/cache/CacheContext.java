package top.jiangliuhong.olcp.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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

    public void addCache(ICache cache) {
        if (StringUtils.isBlank(cache.name())) {
            return;
        }
        for (CacheProperties.Group group : this.properties.getGroups()) {
            if (StringUtils.equals(group.getName(), cache.name())) {
//                log.
                return;
            }
        }
        if (this.cacheMap.containsKey(cache.name())) {

            return;
        }
        this.cacheMap.put(cache.name(), cache);
    }
}
