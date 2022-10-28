package top.jiangliuhong.olcp.data.run;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import top.jiangliuhong.olcp.common.cache.CacheContext;
import top.jiangliuhong.olcp.common.cache.ICache;
import top.jiangliuhong.olcp.common.cache.consts.CacheFactoryNames;
import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.cache.AppCachePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.service.AppService;

import java.util.List;

@Log
public class DataCacheRegister implements CommandLineRunner {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private AppService appService;

    @Override
    public void run(String... args) throws Exception {
        this.initAppCache();
        this.initTableCache();
    }

    private void initAppCache() {
        CacheInfo appCacheInfo = CacheInfo.builder()
                .type(CacheFactoryNames.CAFFEINE)
                .name(CacheNames.APP)
                .build();
        ICache<String, Object> appCache = cacheContext.addCache(appCacheInfo);
        List<AppDO> apps = appService.getAllApp();
        for (AppDO app : apps) {
            AppCachePO ap = new AppCachePO();
            BeanUtils.copyProperties(app, ap);
            appCache.put(ap.getId(), ap);
            appCache.put(ap.getName(), ap);
        }
    }

    private void initTableCache() {
        CacheInfo tableCacheInfo = CacheInfo.builder()
                .type(CacheFactoryNames.CAFFEINE)
                .name(CacheNames.TABLE)
                .build();
        cacheContext.addCache(tableCacheInfo);
    }
}
