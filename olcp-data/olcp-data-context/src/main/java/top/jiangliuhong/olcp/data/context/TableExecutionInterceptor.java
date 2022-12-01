package top.jiangliuhong.olcp.data.context;

import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.component.TableDefinition;

public interface TableExecutionInterceptor {

    void preCreate(TableDefinition table, StringObjectMap values);

}
