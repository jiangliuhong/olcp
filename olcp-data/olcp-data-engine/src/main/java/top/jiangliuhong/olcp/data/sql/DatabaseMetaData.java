package top.jiangliuhong.olcp.data.sql;

import top.jiangliuhong.olcp.data.bean.po.TablePO;

/**
 * 数据库元数据维护
 */
public class DatabaseMetaData {

    public String generateCreateTable(TablePO table) {
        StringBuilder sql = new StringBuilder("CREATE TABLE");

        return sql.toString();
    }
}
