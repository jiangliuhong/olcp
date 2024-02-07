package top.jiangliuhong.olcp.common.handler;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import top.jiangliuhong.olcp.common.ClusterDBContext;

public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return ClusterDBContext.get();
    }
}
