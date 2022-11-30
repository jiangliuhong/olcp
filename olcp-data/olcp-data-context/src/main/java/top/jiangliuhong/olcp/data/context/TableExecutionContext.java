package top.jiangliuhong.olcp.data.context;

import lombok.extern.log4j.Log4j2;
import top.jiangliuhong.olcp.common.jpa.SqlExecutor;
import top.jiangliuhong.olcp.common.utils.LiteStringMap;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.entity.EntityCondition;
import top.jiangliuhong.olcp.data.sql.DataSqlHandler;

import java.util.List;

@Log4j2
public class TableExecutionContext {

    private final SqlExecutor sqlExecutor;
    private final DataSqlHandler dataSqlHandler;

    public TableExecutionContext(SqlExecutor sqlExecutor, DataSqlHandler dataSqlHandler) {
        this.sqlExecutor = sqlExecutor;
        this.dataSqlHandler = dataSqlHandler;
    }

    public StringObjectMap create(TableDefinition tableDefinition, StringObjectMap values) {
        // TODO 公共字段维护,Table操作拦截器
        String insertSql = this.dataSqlHandler.handler().insertSql(tableDefinition, values);
        LiteStringMap<Object> liteStringMap = new LiteStringMap<>();
        tableDefinition.eachFields(field -> {
            liteStringMap.put(field.getName(), values.get(field.getName()));
        });
        // TODO liteStringMap 预览异常
        this.sqlExecutor.executeUpdate(insertSql, liteStringMap);
        return values;
    }

    public void update(TableDefinition tableDefinition, StringObjectMap value) {

    }

    public void batchUpdate(TableDefinition tableDefinition, StringObjectMap updateFieldValue, EntityCondition condition) {

    }

    public void delete(TableDefinition tableDefinition, EntityCondition condition) {

    }

    public List<StringObjectMap> query(TableDefinition tableDefinition, EntityCondition condition) {
        return null;
    }


}
