package top.jiangliuhong.olcp.data.sql;

import top.jiangliuhong.olcp.common.utils.StringMap;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.component.TableFieldDefinition;
import top.jiangliuhong.olcp.data.component.TableDefinition;

/**
 * 数据库sql处理器
 */
public interface IDataSqlHandler {

    public String type();

    public String createSql(TableDefinition table);

    public String addColumn(String tableName, TableFieldDefinition... fieldPOS);

    public String deleteColumn(String tableName, String... columnNames);

    public String updateColumn(String tableName, TableFieldDefinition... fieldPOS);

    public String insertSql(TableDefinition table, StringObjectMap value);

//    public String

}
