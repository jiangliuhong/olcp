package top.jiangliuhong.olcp.data.sql;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.common.jpa.SqlExecutor;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.proxy.TableProxy;
import top.jiangliuhong.olcp.data.type.EngineType;

/**
 * 数据库元数据维护
 */
@Component
public class DatabaseMetaData {

    @Autowired
    private SqlExecutor sqlExecutor;

    public void createTable(TablePO table) {
        String createSql = this.generateCreateTable(table);
        sqlExecutor.executeDDL(createSql);
    }

    public void delColumn() {
        // ALTER TABLE table_name DROP column_name

    }

    public void addColumn() {

    }

    public void updateColumn() {

    }

    private String generateCreateTable(TablePO tableSource) {
        TableProxy table = new TableProxy(tableSource);
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(table.getName()).append("(");
        table.eachFields(field -> {
            sql.append(field.getName()).append(" ");
            sql.append(field.getType()).append(" ");
            if (field.getSource().isRequired()) {
                sql.append(" NOT NULL ");
            }
            if (field.getSource().isUniqueness()) {
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
        sql.append(StringUtils.isNotBlank(table.getSource().getDefaultCharset()) ? table.getSource().getDefaultCharset() : "UTF-8");
        return sql.toString();
    }

}
