package top.jiangliuhong.olcp.data.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.common.jpa.SqlExecutor;
import top.jiangliuhong.olcp.data.entity.TableEntity;
import top.jiangliuhong.olcp.data.entity.TableEntityImpl;
import top.jiangliuhong.olcp.data.sql.DataSqlHandler;

import java.util.List;

@Component
public class TableExecutionContextFactoryImpl implements TableExecutionContextFactory {

    @Autowired
    private SqlExecutor sqlExecutor;
    @Autowired
    private DataSqlHandler dataSqlHandler;
    @Autowired
    private List<TableExecutionInterceptor> interceptorList;

    @Override
    public TableEntity table(String appName) {
        return new TableEntityImpl(appName, new TableExecutionContext(this));
    }

    protected SqlExecutor getSqlExecutor() {
        return this.sqlExecutor;
    }

    protected DataSqlHandler getDataSqlHandler() {
        return this.dataSqlHandler;
    }

    protected List<TableExecutionInterceptor> getInterceptorList() {
        return this.interceptorList;
    }

}
