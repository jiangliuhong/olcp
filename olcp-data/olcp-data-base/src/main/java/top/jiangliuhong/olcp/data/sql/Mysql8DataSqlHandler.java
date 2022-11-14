package top.jiangliuhong.olcp.data.sql;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.bean.proxy.FieldProxy;
import top.jiangliuhong.olcp.data.bean.proxy.TableProxy;
import top.jiangliuhong.olcp.data.type.EngineType;

@Component
public class Mysql8DataSqlHandler implements IDataSqlHandler {

    @Override
    public String type() {
        return "mysql8";
    }

    @Override
    public String createSql(TablePO tableSource) {
        TableProxy table = new TableProxy(tableSource);
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(table.getName()).append("(");
        table.eachFields(field -> {
            sql.append(field.getName()).append(" ");
            sql.append(field.getType()).append(" ");
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
    public String addColumn(String tableName, TableFieldPO... fieldPOS) {
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        if (fieldPOS == null || fieldPOS.length == 0) {
            throw new RuntimeException("fieldPOS is empty");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (TableFieldPO fieldPO : fieldPOS) {
            FieldProxy fieldProxy = new FieldProxy(fieldPO);
            sqlBuilder.append(" ALTER TABLE ")
                    .append(tableName)
                    .append(" ADD ")
                    .append(fieldProxy.getName())
                    .append(" ")
                    .append(fieldProxy.getType());
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
    public String updateColumn(String tableName, TableFieldPO... fieldPOS) {
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        if (fieldPOS == null || fieldPOS.length == 0) {
            throw new RuntimeException("fieldPOS is empty");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (TableFieldPO fieldPO : fieldPOS) {
            FieldProxy fieldProxy = new FieldProxy(fieldPO);
            sqlBuilder.append(" ALTER TABLE ")
                    .append(tableName)
                    .append(" MODIFY ")
                    .append(fieldProxy.getName())
                    .append(" ")
                    .append(fieldProxy.getType())
                    .append(";\n");
        }
        return sqlBuilder.toString();
    }

}
