package top.jiangliuhong.olcp.common.cache.component;

import com.github.benmanes.caffeine.cache.Caffeine;
import top.jiangliuhong.olcp.common.cache.ICache;
import top.jiangliuhong.olcp.common.cache.ICacheFactory;
import top.jiangliuhong.olcp.common.cache.consts.CacheFactoryNames;
import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheFactory implements ICacheFactory {

    @Override
    public String name() {
        return CacheFactoryNames.CAFFEINE;
    }

    @Override
    public <K, V> ICache<K, V> getCache(CacheInfo info) {
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (info.getExpireAfterWrite() != null) {
            builder.expireAfterWrite(info.getExpireAfterWrite(), TimeUnit.SECONDS);
        }
        if (info.getExpireAfterWrite() == null && info.getExpireAfterAccess() != null) {
            builder.expireAfterAccess(info.getExpireAfterAccess(), TimeUnit.SECONDS);
        }
        if (info.getInitialCapacity() != null) {
            builder.initialCapacity(info.getInitialCapacity());
        }
        if (info.getMaximumSize() != null) {
            builder.maximumSize(info.getMaximumSize());
        }
        return new CaffeineCache<>(builder.build(), info.getName());
    }
}
