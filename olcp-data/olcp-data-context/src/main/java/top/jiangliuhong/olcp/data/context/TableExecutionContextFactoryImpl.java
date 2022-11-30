package top.jiangliuhong.olcp.data.context;

import org.springframework.beans.factory.annotation.Autowired;
import top.jiangliuhong.olcp.common.jpa.SqlExecutor;
import top.jiangliuhong.olcp.data.entity.TableEntity;
import top.jiangliuhong.olcp.data.entity.TableEntityImpl;
import top.jiangliuhong.olcp.data.sql.DataSqlHandler;

public class TableExecutionContextFactoryImpl implements TableExecutionContextFactory {

    @Autowired
    private SqlExecutor sqlExecutor;
    @Autowired
    private DataSqlHandler dataSqlHandler;

    @Override
    public TableEntity table(String appName) {
        return new TableEntityImpl(appName, new TableExecutionContext(sqlExecutor, dataSqlHandler));
    }
}
