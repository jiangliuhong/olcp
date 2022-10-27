package top.jiangliuhong.olcp.common.cache;

import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

public interface ICacheFactory {

    public String name();

    public ICache getCache(CacheProperties.Group group);
}
