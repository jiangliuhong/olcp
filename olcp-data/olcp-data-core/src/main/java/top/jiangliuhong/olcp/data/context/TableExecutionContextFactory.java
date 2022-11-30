package top.jiangliuhong.olcp.data.context;

import top.jiangliuhong.olcp.data.entity.TableEntity;

public interface TableExecutionContextFactory {

    public TableEntity table(String appName);
}
