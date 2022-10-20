package top.jiangliuhong.olcp.common;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.common.handler.DefaultBeforeConvertCallback;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * database configure
 */
@Configuration
@EnableJdbcRepositories(basePackages = "top.jiangliuhong.olcp")
public class DatabaseAutoConfigure extends AbstractJdbcConfiguration {


    @Resource
    private DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
        //return DataSourceBuilder.create().build();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public BeforeConvertCallback<BaseDO> beforeConvertCallback() {
        return new DefaultBeforeConvertCallback();
    }


}
