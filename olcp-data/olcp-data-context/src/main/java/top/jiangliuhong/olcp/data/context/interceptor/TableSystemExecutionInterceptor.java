package top.jiangliuhong.olcp.data.context.interceptor;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.auth.bean.SimpleUserDO;
import top.jiangliuhong.olcp.auth.utils.AuthUtils;
import top.jiangliuhong.olcp.common.utils.IdUtils;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.consts.SystemFieldNames;
import top.jiangliuhong.olcp.data.context.TableExecutionInterceptor;

import java.sql.Timestamp;

@Component
@Order(100)
public class TableSystemExecutionInterceptor implements TableExecutionInterceptor {

    @Override
    public void preCreate(TableDefinition table, StringObjectMap values) {
        String primaryFieldName = table.getPrimaryFieldName();
        if (!values.containsKey(primaryFieldName)) {
            values.put(primaryFieldName, IdUtils.generate());
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (table.hasField(SystemFieldNames.CREATE_TIME)) {
            values.put(SystemFieldNames.CREATE_TIME, currentTime);
        }
        if (table.hasField(SystemFieldNames.UPDATE_TIME)) {
            values.put(SystemFieldNames.UPDATE_TIME, currentTime);
        }
        SimpleUserDO currentUser = AuthUtils.getCurrentUser();
        String user = "unknown";
        if (currentUser != null) {
            user = currentUser.getId();
        }
        if (table.hasField(SystemFieldNames.CREATE_USER)) {
            values.put(SystemFieldNames.CREATE_USER, user);
        }
        if (table.hasField(SystemFieldNames.UPDATE_USER)) {
            values.put(SystemFieldNames.UPDATE_USER, user);
        }
    }

}
