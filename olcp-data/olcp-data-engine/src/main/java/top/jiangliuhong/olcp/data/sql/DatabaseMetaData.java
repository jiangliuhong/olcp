package top.jiangliuhong.olcp.data.sql;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.common.jpa.SqlExecutor;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;

/**
 * 数据库元数据维护
 */
@Component
public class DatabaseMetaData {

    @Autowired
    private SqlExecutor sqlExecutor;
    @Autowired
    private DataSqlHandler dataSqlHandler;

    public void createTable(TablePO table) {
        if (table == null || CollectionUtils.isEmpty(table.getFields())) {
            return;
        }
        String createSql = dataSqlHandler.handler().createSql(table);
        sqlExecutor.executeDDL(createSql);
    }

    public void deleteColumn(String tableName, String... columnNames) {
        // ALTER TABLE <表名> DROP <字段名>；
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        if (columnNames == null || columnNames.length == 0) {
            return;
        }
        String deleteColumnSql = dataSqlHandler.handler().deleteColumn(tableName, columnNames);
        sqlExecutor.executeDDL(deleteColumnSql);
    }

    public void addColumn(String tableName, TableFieldPO... fieldPOS) {
        // ALTER TABLE <表名> ADD <新字段名><数据类型>[约束条件];
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        if (fieldPOS == null || fieldPOS.length == 0) {
            return;
        }
        String addColumnSql = dataSqlHandler.handler().addColumn(tableName, fieldPOS);
        sqlExecutor.executeDDL(addColumnSql);
    }

    public void updateColumn(String tableName, TableFieldPO... fieldPOS) {
        // ALTER TABLE <表名> MODIFY <字段名> <数据类型>
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        if (fieldPOS == null || fieldPOS.length == 0) {
            return;
        }

        String updateColumnSql = dataSqlHandler.handler().updateColumn(tableName, fieldPOS);
        sqlExecutor.executeDDL(updateColumnSql);
    }

}
