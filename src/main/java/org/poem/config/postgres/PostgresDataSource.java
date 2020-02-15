package org.poem.config.postgres;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.poem.utils.ContextDatabase;
import org.poem.utils.DataSourceDriverHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author sangfor
 */
@Data
@Configuration
@Component
@ConditionalOnExpression("environment['spring.warehouse.postgres.url'] != ''  && environment['spring.warehouse.postgres.url'] != null  ")
@ConfigurationProperties(prefix = "spring.warehouse.postgres")
public class PostgresDataSource {

    private static final Logger logger = LoggerFactory.getLogger(PostgresDataSource.class);

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    /**
     * @return
     */
    @Bean(name = "postgresHikariData")
    public DataSource hiveHikariDataSource() {
        logger.info("\nInit postgres  DataBase [" + DataSourceDriverHelper.getDatasourceType(url) + "] : " +
                "\n\t\t [driver]:" + DataSourceDriverHelper.getJdbc(url) +
                "\n\t\t [jdbcUrl]:" + url +
                "\n\t\t [user]:" + username +
                "\n\t\t [password]:" + password
        );
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(DataSourceDriverHelper.getJdbc(url));
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(30);
        dataSource.setConnectionTestQuery("select 1");
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "500");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "5000");
        dataSource.setIdleTimeout(300000);
        dataSource.setValidationTimeout(60000);
        dataSource.setMaxLifetime(600000);
        return dataSource;
    }


    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate mysqlJdbcExcuetor(@Qualifier("postgresHikariData") DataSource hiveHikariDataSource) {
        ContextDatabase.setSourceSchema(DataSourceDriverHelper.getDatasourceType(url));
        ContextDatabase.setSourceCatalog(DataSourceDriverHelper.getCatalog(url));
        return new JdbcTemplate(hiveHikariDataSource);
    }
}
