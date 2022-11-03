package top.jiangliuhong.olcp.data.sql;

import top.jiangliuhong.olcp.data.bean.cache.TableCachePO;

public class DatabaseMetaDataSql {

    public String generateCreateTable(TableCachePO table) {
        StringBuilder sql = new StringBuilder("CREATE TABLE");

        return sql.toString();
    }
}
