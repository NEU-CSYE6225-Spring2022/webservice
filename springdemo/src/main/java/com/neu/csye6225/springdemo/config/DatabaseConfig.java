package com.neu.csye6225.springdemo.config;

import com.neu.csye6225.springdemo.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class DatabaseConfig {

    @Value("${databasehost}")
    private String databasehost;

    @Value("${databaseuser}")
    private String databaseuser;

    @Value("${databasepassword}")
    private String databasepassword;

    @Value("${database}")
    private String database;

    @Bean
    public DataSource getDataSource()
    {
        String certiifcate = new File("src/main/resources/rds-combined-ca-bundle.pem").getAbsolutePath();

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        if(database.equalsIgnoreCase(Constants.MYSQL)){
            dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
            dataSourceBuilder.url("jdbc:mysql://"+ databasehost +"/csye6225?useSSL=true&serverTimezone=UTC&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true&sslmode=VERIFY_CA"
                +"&sslrootcert="+ certiifcate);
        }else{
            dataSourceBuilder.driverClassName("org.h2.Driver");
            dataSourceBuilder.url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        }
        dataSourceBuilder.username(databaseuser);
        dataSourceBuilder.password(databasepassword);
        return dataSourceBuilder.build();
    }
}
