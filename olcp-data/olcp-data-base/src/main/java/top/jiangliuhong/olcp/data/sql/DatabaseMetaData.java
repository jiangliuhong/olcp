package top.jiangliuhong.olcp.data.sql;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.common.jpa.SqlExecutor;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.common.utils.NameUtils;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.component.TableFieldDefinition;

import java.util.ArrayList;
import java.util.List;

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
        String createSql = dataSqlHandler.handler().createSql(new TableDefinition(table));
        sqlExecutor.executeDDL(createSql);
    }

    public void updateTable(TablePO table, List<String> deleteFields, List<TableFieldPO> updateFields, List<TableFieldPO> addFields) {
        TableDefinition tableDefinition = new TableDefinition(table);
        if (CollectionUtils.isNotEmpty(deleteFields)) {
            String[] deleted = deleteFields.toArray(new String[0]);
            for (int i = 0; i < deleted.length; i++) {
                deleted[i] = NameUtils.camelToUnderline(deleted[i]);
            }
            this.deleteColumn(tableDefinition.getDbName(), deleted);
        }
        if (CollectionUtils.isNotEmpty(updateFields)) {
            TableFieldPO[] addFieldArray = BeanUtils.copyBean(updateFields, TableFieldPO.class)
                    .toArray(new TableFieldPO[0]);
            this.updateColumn(tableDefinition.getDbName(), addFieldArray);
        }
        if (CollectionUtils.isNotEmpty(addFields)) {
            TableFieldPO[] addFieldArray = BeanUtils.copyBean(addFields, TableFieldPO.class)
                    .toArray(new TableFieldPO[0]);
            this.addColumn(tableDefinition.getDbName(), addFieldArray);
        }
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
        String addColumnSql = dataSqlHandler.handler().addColumn(tableName, convertFieldDefinition(fieldPOS));
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

        String updateColumnSql = dataSqlHandler.handler().updateColumn(tableName, convertFieldDefinition(fieldPOS));
        sqlExecutor.executeDDL(updateColumnSql);
    }

    private TableFieldDefinition[] convertFieldDefinition(TableFieldPO[] fieldPOS) {
        List<TableFieldDefinition> list = new ArrayList<>();
        for (TableFieldPO fieldPO : fieldPOS) {
            list.add(new TableFieldDefinition(fieldPO));
        }
        return list.toArray(new TableFieldDefinition[0]);
    }

}
