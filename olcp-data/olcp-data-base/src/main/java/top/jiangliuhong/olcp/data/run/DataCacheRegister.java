package top.jiangliuhong.olcp.data.run;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import top.jiangliuhong.olcp.common.cache.CacheContext;
import top.jiangliuhong.olcp.common.cache.consts.CacheFactoryNames;
import top.jiangliuhong.olcp.common.cache.properties.CacheInfo;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.po.GroovyFilePO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.service.AppService;
import top.jiangliuhong.olcp.data.service.GroovyFileService;
import top.jiangliuhong.olcp.data.service.TableService;

import java.util.List;

@Log4j2
@Order(10)
public class DataCacheRegister implements CommandLineRunner {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private AppService appService;
    @Autowired
    private TableService tableService;
    @Autowired
    private GroovyFileService groovyFileService;

    @Override
    public void run(String... args) throws Exception {
        log.info("start load data base cache");
        this.buildSysCache(
                CacheNames.APP_ID,
                CacheNames.APP_NAME,
                CacheNames.TABLE_ID,
                CacheNames.TABLE_NAME,
                CacheNames.GROOVY_FILE
        );
        this.initAppCache();
        String[] serverAppIds = appService.getServerAppIds();
        this.initTableCache(serverAppIds);
        this.initGroovyFileCache(serverAppIds);
        log.info("end load data base cache");
    }

    private void initAppCache() {
        List<AppDO> apps = appService.getAllApp();
        for (AppDO app : apps) {
            appService.saveCache(app);
        }
    }

    private void initTableCache(String[] serverAppIds) {
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

    private void initGroovyFileCache(String[] serverAppIds) {
        List<GroovyFilePO> files = groovyFileService.getFiles(serverAppIds);
        files.forEach(file -> groovyFileService.saveCache(file));
    }
}
