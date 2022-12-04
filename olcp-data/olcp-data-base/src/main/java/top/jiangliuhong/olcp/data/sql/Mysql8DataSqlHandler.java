package top.jiangliuhong.olcp.data.sql;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.common.utils.StringMap;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.component.TableFieldDefinition;
import top.jiangliuhong.olcp.data.type.EngineType;
import top.jiangliuhong.olcp.data.utils.FieldTypeUtils;

@Component
public class Mysql8DataSqlHandler implements IDataSqlHandler {

    @Override
    public String type() {
        return "mysql8";
    }

    @Override
    public String createSql(TableDefinition table) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(table.getDbName()).append("(");
        table.eachFields(field -> {
            sql.append(field.getDbName()).append(" ");
            sql.append(field.getDbType()).append(" ");
            if (field.getSource().getRequired()) {
                sql.append(" NOT NULL ");
            }
            if (field.getSource().getUniqueness()) {
                sql.append(" UNIQUE ");
            }
            if (field.getSource().getDefaultValue() != null) {
                sql.append(" DEFAULT '").append(field.getSource().getDefaultValue()).append("'");
            }
            sql.append(",");
        });
        sql.append("PRIMARY KEY ").append("(id)");
        sql.append(") ENGINE=");
        sql.append(table.getSource().getEngineType() == null ? EngineType.InnoDB : table.getSource().getEngineType());
        sql.append(" CHARSET=");
        sql.append(StringUtils.isNotBlank(table.getSource().getDefaultCharset()) ? table.getSource().getDefaultCharset() : "utf8");
        sql.append(";");
        return sql.toString();
    }

    @Override
    public String addColumn(String tableName, TableFieldDefinition... fieldPOS) {
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        if (fieldPOS == null || fieldPOS.length == 0) {
            throw new RuntimeException("fieldPOS is empty");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (TableFieldDefinition fieldPO : fieldPOS) {
            sqlBuilder.append(" ALTER TABLE ")
                    .append(tableName)
                    .append(" ADD ")
                    .append(fieldPO.getDbName())
                    .append(" ")
                    .append(fieldPO.getDbType());
        }
        return sqlBuilder.toString();
    }

    @Override
    public String deleteColumn(String tableName, String... columnNames) {
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        if (columnNames == null || columnNames.length == 0) {
            throw new RuntimeException("columnNames is empty");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (String columnName : columnNames) {
            sqlBuilder.append("ALTER TABLE ")
                    .append(tableName)
                    .append(" DROP ")
                    .append(columnName)
                    .append(";\n");
        }
        return sqlBuilder.toString();
    }

    @Override
    public String updateColumn(String tableName, TableFieldDefinition... fieldPOS) {
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        if (fieldPOS == null || fieldPOS.length == 0) {
            throw new RuntimeException("fieldPOS is empty");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (TableFieldDefinition fieldPO : fieldPOS) {
            sqlBuilder.append(" ALTER TABLE ")
                    .append(tableName)
                    .append(" MODIFY ")
                    .append(fieldPO.getDbName())
                    .append(" ")
                    .append(fieldPO.getDbType())
                    .append(";\n");
        }
        return sqlBuilder.toString();
    }

    @Override
    public String insertSql(TableDefinition table) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        sqlBuilder.append(table.getDbName());
        StringBuilder valueBuilder = new StringBuilder("(");
        sqlBuilder.append("(");
        table.eachFields(field -> {
            sqlBuilder.append(field.getDbName()).append(",");
            valueBuilder.append(":").append(field.getName());
            valueBuilder.append(",");
        });
        valueBuilder.deleteCharAt(valueBuilder.length() - 1);
        valueBuilder.append(")");
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        sqlBuilder.append(")").append(" VALUES ").append(valueBuilder);
        return sqlBuilder.toString();
    }
}
