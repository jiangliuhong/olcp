package top.jiangliuhong.olcp.data.service;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;

public class SysAppCache {

    private final AppPO app;

    public SysAppCache(String appId) {
        if (StringUtils.isBlank(appId)) {
            throw new NullPointerException("appId is empty");
        }
        this.app = CacheUtils.getCacheValue(CacheNames.APP_ID, appId);
        if (this.app == null) {
            throw new RuntimeException("not found app by " + appId);
        }
    }

    public AppPO getApp() {
        return this.app;
    }

    public boolean tableExist(String tableName) {
        String keyName = this.getAppModuleKeyName(this.app.getName(), tableName);
        return CacheUtils.exist(CacheNames.TABLE_NAME, keyName);
    }

    public TablePO getTable(String tableName) {
        String keyName = this.getAppModuleKeyName(this.app.getName(), tableName);
        return CacheUtils.getCacheValue(CacheNames.TABLE_NAME, keyName);
    }

    public void saveTable(TablePO table) {
        String keyName = this.getAppModuleKeyName(this.app.getName(), table.getName());
        CacheUtils.putCacheValue(CacheNames.TABLE_NAME, keyName, table);
    }

    private String getAppModuleKeyName(String appName, String moduleName) {
        return appName + "." + moduleName;
    }
}
