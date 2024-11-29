package com.reppuhallinta.inventory_management_sys.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Configuration class for setting up data sources.
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${app.datasource.schema.default}")
    private String defaultSchema;

    @Value("${app.datasource.schema.az}")
    private String azSchema;

    @Value("${app.datasource.schema.fi}")
    private String fiSchema;

    @Value("${app.datasource.schema.jp}")
    private String jpSchema;

   /**
     * Configures the data source with routing based on the current language.
     * 
     * @return the configured DataSource
    */
   @Bean
    public DataSource dataSource() {
        // Define data sources for each language
        Map<Object, Object> dataSources = new HashMap<>();
        
        // Add data sources for each schema
        dataSources.put("EN", createDataSource(defaultSchema, username, password));
        dataSources.put("AZ", createDataSource(azSchema, username, password));
        dataSources.put("FI", createDataSource(fiSchema, username, password));
        dataSources.put("JP", createDataSource(jpSchema, username, password));

        // Create the routing data source
        LanguageRoutingDataSource routingDataSource = new LanguageRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);

        // Set default data source (e.g., English schema)
        routingDataSource.setDefaultTargetDataSource(dataSources.get("EN"));

        routingDataSource.afterPropertiesSet(); // Finalize the setup

        return routingDataSource;
    }

    /**
     * Creates a data source for the given schema.
     * 
     * @param url the URL of the schema
     * @param username the username for the data source
     * @param password the password for the data source
     * @return the created DataSource
     */
    private DataSource createDataSource(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username); // Replace with actual username
        dataSource.setPassword(password); // Replace with actual password
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Replace if using another driver
        return dataSource;
    }

}
