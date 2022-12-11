package top.jiangliuhong.olcp.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.bean.PageInfo;
import top.jiangliuhong.olcp.data.bean.TablePageQueryParam;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.component.SysAppCache;
import top.jiangliuhong.olcp.data.context.TableExecutionContextFactory;
import top.jiangliuhong.olcp.data.entity.EntityFind;
import top.jiangliuhong.olcp.data.entity.EntityValue;

/**
 * 应用管理服务
 */
@Service
public class AppTableService {

    @Autowired
    private TableExecutionContextFactory factory;

    public TablePO getTable(String appName, String tableName) {
        SysAppCache sysAppCache = SysAppCache.forName(appName);
        if (sysAppCache == null) {
            return null;
        }
        return sysAppCache.getTable(tableName);
    }

    /**
     * 分页查询表数据
     *
     * @param param 查询条件
     * @return EntityValue List
     */
    public PageInfo<EntityValue> queryTableDate(String appName, String tableName, TablePageQueryParam param) {
        EntityFind find = factory.table(appName).find(tableName);
        find.offset(param.getPage(), param.getSize());
        return find.page();
    }
}
