package top.jiangliuhong.olcp.auth.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import top.jiangliuhong.olcp.auth.consts.AuthConst;
import top.jiangliuhong.olcp.auth.properties.AuthProperties;
import top.jiangliuhong.olcp.common.cache.CacheContext;
import top.jiangliuhong.olcp.common.cache.consts.CacheFactoryNames;
import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;

public class AuthRunner implements CommandLineRunner {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private AuthProperties properties;

    @Override
    public void run(String... args) {
        Integer expiration = properties.getUserCacheExpire();
        CacheInfo userCacheInfo = CacheInfo.builder()
                .type(CacheFactoryNames.CAFFEINE)
                .expireAfterWrite(expiration)
                .name(AuthConst.AUTH_USER_CACHE_NAME)
                .build();
        cacheContext.addCache(userCacheInfo);
    }

}
