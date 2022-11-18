package top.jiangliuhong.olcp.data.component;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;

/**
 * 系统应用缓存
 */
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
        String tableId = CacheUtils.getCacheValue(CacheNames.TABLE_NAME, keyName);
        if (StringUtils.isBlank(tableId)) {
            return null;
        }
        return CacheUtils.getCacheValue(CacheNames.TABLE_ID, tableId);
    }

    public void saveTable(TablePO table) {
        CacheUtils.putCacheValue(CacheNames.TABLE_ID, table.getId(), table);
        String keyName = this.getAppModuleKeyName(this.app.getName(), table.getName());
        CacheUtils.putCacheValue(CacheNames.TABLE_NAME, keyName, table.getId());
    }

    private String getAppModuleKeyName(String appName, String moduleName) {
        return appName + "." + moduleName;
    }
}
