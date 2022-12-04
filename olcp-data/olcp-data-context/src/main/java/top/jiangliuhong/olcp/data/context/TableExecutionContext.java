package top.jiangliuhong.olcp.data.context;

import lombok.extern.log4j.Log4j2;
import top.jiangliuhong.olcp.common.utils.LiteStringMap;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.entity.EntityCondition;

import java.util.List;

@Log4j2
public class TableExecutionContext {

    private final TableExecutionContextFactoryImpl factory;

    public TableExecutionContext(TableExecutionContextFactoryImpl factory) {
        this.factory = factory;
    }

    public TableExecutionContextFactory getFactory() {
        return this.factory;
    }

    public StringObjectMap create(TableDefinition tableDefinition, StringObjectMap values) {
        // Table操作拦截器
        List<TableExecutionInterceptor> interceptorList = this.factory.getInterceptorList();
        if (interceptorList != null) {
            interceptorList.forEach(interceptor -> {
                interceptor.preCreate(tableDefinition, values);
            });
        }
        String insertSql = this.factory.getDataSqlHandler().handler().insertSql(tableDefinition);
        LiteStringMap<Object> liteStringMap = new LiteStringMap<>();
        tableDefinition.eachFields(field -> {
            liteStringMap.put(field.getName(), values.get(field.getName()));
        });
        this.factory.getSqlExecutor().executeUpdate(insertSql, liteStringMap);
        return values;
    }

    public void update(TableDefinition tableDefinition, StringObjectMap value) {

    }

    public void batchUpdate(TableDefinition tableDefinition, StringObjectMap updateFieldValue, EntityCondition condition) {

    }

    public void delete(TableDefinition tableDefinition, EntityCondition condition) {

    }

    public List<StringObjectMap> query(TableDefinition tableDefinition, String querySql, Object... parameters) {
        return this.factory.getSqlExecutor().executeQuery(querySql, new TableDataResultTransformer(tableDefinition), parameters);
    }


}
