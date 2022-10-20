package top.jiangliuhong.olcp.engine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * app cache for caffeine
 */
@Component
public class AppCache {

    private Map<String, Cache> cacheMaps = new HashMap<>();

    Cache<String, Object> cache;

    @PostConstruct
    public void initCache() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .build();
    }

    public <T> Cache<String, T> getCache(String name) {
        if (cacheMaps.containsKey(name)) {
            return (Cache<String, T>) cacheMaps.get(name);
        }
        return null;
    }


}
