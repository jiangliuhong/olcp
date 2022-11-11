package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.common.utils.NameUtils;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TableFieldUpdateResPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.proxy.TableProxy;
import top.jiangliuhong.olcp.data.sql.DatabaseMetaData;

import javax.transaction.Transactional;

/**
 * 系统元数据服务
 */
@Service
public class DatabaseMetaDataService {

    @Autowired
    private DatabaseMetaData databaseMetaData;
    @Autowired
    private TableService tableService;

    @Transactional
    public String addTable(TablePO table) {
        tableService.addTable(table);
        // create table ddl
        databaseMetaData.createTable(table);
        return table.getId();
    }

    @Transactional
    public void updateTable(TablePO table) {
        TableFieldUpdateResPO updateRes = tableService.updateTable(table);
        TableProxy tableProxy = new TableProxy(table);
        if (CollectionUtils.isNotEmpty(updateRes.getDeletes())) {
            String[] deleted = updateRes.getDeletes().toArray(new String[0]);
            for (int i = 0; i < deleted.length; i++) {
                deleted[i] = NameUtils.camelToUnderline(deleted[i]);
            }
            databaseMetaData.deleteColumn(tableProxy.getName(), deleted);
        }
        if (CollectionUtils.isNotEmpty(updateRes.getUpdates())) {
            TableFieldPO[] addFields = BeanUtils.copyBean(updateRes.getUpdates(), TableFieldPO.class)
                    .toArray(new TableFieldPO[0]);
            databaseMetaData.updateColumn(tableProxy.getName(), addFields);
        }
        if (CollectionUtils.isNotEmpty(updateRes.getCreates())) {
            TableFieldPO[] addFields = BeanUtils.copyBean(updateRes.getCreates(), TableFieldPO.class)
                    .toArray(new TableFieldPO[0]);
            databaseMetaData.updateColumn(tableProxy.getName(), addFields);
        }
    }

}
