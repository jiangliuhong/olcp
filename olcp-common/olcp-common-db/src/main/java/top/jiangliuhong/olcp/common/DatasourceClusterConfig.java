package top.jiangliuhong.olcp.common;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import top.jiangliuhong.olcp.common.handler.RoutingDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@ConditionalOnProperty(value = "spring.datasource.enable-cluster", havingValue = "true")
public class DatasourceClusterConfig {

    private Map<String, DataSourceProperties> cluster = new HashMap<>();

    @Bean
    public List<DataSource> dataSources(@Autowired DataSourceProperties dataSourceProperties) {
        List<DataSource> list = new ArrayList<>();
        // 处理默认的master datasource
        ClusterDBContext.registry("master0", createDataSource(dataSourceProperties));
        this.cluster.forEach((key, val) -> {
            DataSource dataSource2 = createDataSource(val);
            ClusterDBContext.registry(key, dataSource2);
        });
        return list;
    }

    private DataSource createDataSource(DataSourceProperties properties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClassName());
        return dataSource;
    }

    @Bean
    @Primary
    @DependsOn("dataSources")
    public DataSource routingDataSource() {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(ClusterDBContext.getDefaultMaster());
        routingDataSource.setTargetDataSources(ClusterDBContext.dataSources);
        return routingDataSource;
    }

    @Bean
    public DataSourceAop dataSourceAop() {
        return new DataSourceAop();
    }
}
