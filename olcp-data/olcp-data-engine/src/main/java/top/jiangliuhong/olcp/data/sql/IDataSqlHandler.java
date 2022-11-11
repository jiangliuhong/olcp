package top.jiangliuhong.olcp.data.sql;

import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;

/**
 * 数据库sql处理器
 */
public interface IDataSqlHandler {

    public String type();

    public String createSql(TablePO table);

    public String addColumn(String tableName, TableFieldPO... fieldPOS);

    public String deleteColumn(String tableName, String... columnNames);

    public String updateColumn(String tableName, TableFieldPO... fieldPOS);

}
