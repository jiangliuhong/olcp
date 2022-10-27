package top.jiangliuhong.olcp.common.cache.component;

import top.jiangliuhong.olcp.common.cache.ICache;
import top.jiangliuhong.olcp.common.cache.ICacheFactory;
import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

public class CaffeineCacheFactory implements ICacheFactory {

//    public <K, V> ICache<K, V> getCache() {
//        return null;
//    }
//
//
//    public <T> Cache<String, T> getCache2() {
//        return Caffeine.newBuilder()
//                // 设置最后一次写入或访问后经过固定时间过期
//                .expireAfterWrite(60, TimeUnit.SECONDS)
//                // 初始的缓存空间大小
//                .initialCapacity(100)
//                // 缓存的最大条数
//                .maximumSize(1000)
//                .build();
//    }


    @Override
    public String name() {
        return "caffeine";
    }

    @Override
    public ICache getCache(CacheProperties.Group group) {
        return null;
    }
}
