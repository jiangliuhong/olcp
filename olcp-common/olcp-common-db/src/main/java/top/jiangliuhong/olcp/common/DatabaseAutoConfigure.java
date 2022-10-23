package top.jiangliuhong.olcp.common;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;
import top.jiangliuhong.olcp.common.handler.BaseFieldInterceptor;
import top.jiangliuhong.olcp.common.handler.EntityInterceptorConfig;
import top.jiangliuhong.olcp.common.handler.IEntityInterceptor;

/**
 * database configure
 */
@Configuration
// @EnableTransactionManagement
@EnableJpaRepositories(basePackages = "top.jiangliuhong.olcp")
@EntityScan(basePackages = "top.jiangliuhong.olcp")
public class DatabaseAutoConfigure {

    @Resource
    private DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("top.jiangliuhong.olcp");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(additionalProperties());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    Properties additionalProperties() {
        // Map<String, Object> jpaProperties = new HashMap<>();
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        // properties.setProperty("hibernate.ejb.interceptor",
        // "top.jiangliuhong.olcp.common.handler.CommonInterceptor");
        // jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        return properties;
    }

    @Bean
    public BaseFieldInterceptor baseFieldInterceptor() {
        return new BaseFieldInterceptor();
    }

    @Bean
    @Autowired(required = false)
    public EntityInterceptorConfig entityInterceptorConfig(List<IEntityInterceptor> interceptors) {
        return new EntityInterceptorConfig(interceptors);
    }
}
