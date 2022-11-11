package top.jiangliuhong.olcp.data.run;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import top.jiangliuhong.olcp.common.cache.CacheContext;
import top.jiangliuhong.olcp.common.cache.consts.CacheFactoryNames;
import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.service.AppService;
import top.jiangliuhong.olcp.data.service.TableService;

import java.util.List;

@Log
@Order(10)
public class DataCacheRegister implements CommandLineRunner {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private AppService appService;
    @Autowired
    private TableService tableService;

    @Override
    public void run(String... args) throws Exception {
        this.buildSysCache(
                CacheNames.APP_ID,
                CacheNames.APP_NAME,
                CacheNames.TABLE_ID,
                CacheNames.TABLE_NAME
        );
        this.initAppCache();
        this.initTableCache();
    }

    private void initAppCache() {
        List<AppDO> apps = appService.getAllApp();
        for (AppDO app : apps) {
            appService.saveCache(app);
        }
    }

    private void initTableCache() {
        String[] serverAppIds = appService.getServerAppIds();
        List<TablePO> tables = tableService.getAllTableByApp(serverAppIds);
        tables.forEach(table -> tableService.saveCache(table));
    }

    private void buildSysCache(String... cacheName) {
        for (String name : cacheName) {
            CacheInfo cache = CacheInfo.builder()
                    .type(CacheFactoryNames.CAFFEINE)
                    .name(name)
                    .build();
            cacheContext.addCache(cache);
        }
    }
}
