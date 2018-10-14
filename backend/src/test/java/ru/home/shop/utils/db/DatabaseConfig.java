package ru.home.shop.utils.db;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.jooq.conf.Settings;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@TestConfiguration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource ds = DataSourceBuilder.create()
                .url("jdbc:h2:mem:test")
                .build();

        return ProxyDataSourceBuilder.create(ds)
                .logQueryBySlf4j(SLF4JLogLevel.INFO)
                .multiline()
                .countQuery()
                .build();
    }

    @Bean
    public Settings settings() {
        return new Settings()
                .withRenderSchema(false);
    }
}
