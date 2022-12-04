package top.jiangliuhong.olcp.data.context;

import top.jiangliuhong.olcp.data.entity.TableEntity;

public interface TableExecutionContextFactory extends ExecutionContext {

    public TableEntity table(String appName);
}
