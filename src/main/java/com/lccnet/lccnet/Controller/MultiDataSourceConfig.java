package com.lccnet.lccnet.Controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MultiDataSourceConfig {


    @Primary
    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.product")
    public DataSource dataSourceProduct() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource dataSourceOrder() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "productJdbcTemplate")
    public JdbcTemplate productJdbcTemplate(@Qualifier("productDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean(name = "orderJdbcTemplate")
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
