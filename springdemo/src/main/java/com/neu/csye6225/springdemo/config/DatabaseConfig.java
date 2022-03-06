package com.neu.csye6225.springdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${databasehost}")
    private String databasehost;

    @Value("${databaseuser}")
    private String databaseuser;

    @Value("${databasepassword}")
    private String databasepassword;

    @Bean
    public DataSource getDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://"+ databasehost +"/cyse2022?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true&useSSL=false");
        dataSourceBuilder.username(databaseuser);
        dataSourceBuilder.password(databasepassword);
        return dataSourceBuilder.build();
    }
}
