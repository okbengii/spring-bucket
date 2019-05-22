package com.okbeng.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源 使用 Druid
 * 
 * @author apple
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.maxActive}")
    private Integer maxActive;
    @Value("${spring.datasource.maxWait}")
    private Integer maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    @Bean
    @Primary
    public DruidDataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        // configuration
        if (initialSize != null) {
            datasource.setInitialSize(initialSize);
        }
        if (minIdle != null) {
            datasource.setMinIdle(minIdle);
        }
        if (maxActive != null) {
            datasource.setMaxActive(maxActive);
        }
        if (maxWait != null) {
            datasource.setMaxWait(maxWait);
        }
        if (timeBetweenEvictionRunsMillis != null) {
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }
        if (minEvictableIdleTimeMillis != null) {
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        if (validationQuery != null) {
            datasource.setValidationQuery(validationQuery);
        }
        if (testWhileIdle != null) {
            datasource.setTestWhileIdle(testWhileIdle);
        }
        if (testOnBorrow != null) {
            datasource.setTestOnBorrow(testOnBorrow);
        }
        if (testOnReturn != null) {
            datasource.setTestOnReturn(testOnReturn);
        }
        if (poolPreparedStatements != null) {
            datasource.setPoolPreparedStatements(poolPreparedStatements);
        }
        if (maxPoolPreparedStatementPerConnectionSize != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }
        return datasource;
    }
}
