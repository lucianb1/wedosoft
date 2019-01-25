package eu.wedosoft.contact.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Contains database configurations.
 * Also we initialize here the dataSource using the settings from application.yml - spring.datasource
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfig extends HikariConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        return new HikariDataSource(this);
    }
}

