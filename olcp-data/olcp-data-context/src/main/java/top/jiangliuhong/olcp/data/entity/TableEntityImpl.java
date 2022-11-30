package top.jiangliuhong.olcp.data.entity;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.component.SysAppCache;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.context.TableExecutionContext;
import top.jiangliuhong.olcp.data.exception.AppNotFoundException;
import top.jiangliuhong.olcp.data.exception.TableNotFoundException;

public class TableEntityImpl implements TableEntity {

    private final SysAppCache appCache;

    private final TableExecutionContext executionContext;

    public TableEntityImpl(String appName, TableExecutionContext executionContext) {
        String appId = CacheUtils.getCacheValue(CacheNames.APP_NAME, appName);
        if (StringUtils.isBlank(appId)) {
            throw new AppNotFoundException("未找到" + appName + "应用");
        }
        this.appCache = new SysAppCache(appId);
        this.executionContext = executionContext;
    }

    @Override
    public String getAppName() {
        return this.appCache.getApp().getName();
    }

    @Override
    public EntityValue value(String tableName) {
        TablePO table = this.appCache.getTable(tableName);
        if (table == null) {
            throw new TableNotFoundException("未找到" + tableName);
        }
        return new TableEntityValueImpl(table, this, executionContext);
    }

    @Override
    public EntityFind find(String tableName) {
        return null;
    }


}
