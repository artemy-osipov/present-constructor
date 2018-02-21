package ru.home.shop.utils.db;

import org.jooq.conf.Settings;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@TestConfiguration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource(MySQLContainer mySQLContainer) {
        return new DriverManagerDataSource(mySQLContainer.getJdbcUrl(), mySQLContainer.getUsername(), mySQLContainer.getPassword());
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MySQLContainer mySQLContainer() {
        return new MySQLContainer("mysql:5.7.21")
                .withDatabaseName("presents-test");
    }

    @Bean
    public Settings settings() {
        return new Settings()
                .withRenderSchema(false);
    }
}
