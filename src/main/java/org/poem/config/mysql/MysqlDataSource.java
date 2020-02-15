package org.poem.config.mysql;

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
@Configuration
@Component
@Data
@ConditionalOnExpression("environment['spring.warehouse.mysql.url'] != ''  && environment['spring.warehouse.mysql.url'] != null  ")
@ConfigurationProperties(prefix = "spring.warehouse.mysql")
public class MysqlDataSource {

    private static final Logger logger = LoggerFactory.getLogger(MysqlDataSource.class);

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean(name = "mysqlHikariData")
    public DataSource hiveHikariDataSource() {
        logger.info("\nInit mysql  DataBase [" + DataSourceDriverHelper.getDatasourceType(url) + "] : " +
                "\n\t\t [driverClassName]:" + DataSourceDriverHelper.getJdbc(url) +
                "\n\t\t [url]:" + url +
                "\n\t\t [user]:" + username +
                "\n\t\t [password]:" + password
        );
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(DataSourceDriverHelper.getJdbc(url));
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(30);
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "500");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "5000");
        dataSource.setIdleTimeout(300000);
        dataSource.setValidationTimeout(60000);
        dataSource.setMaxLifetime(600000);
        return dataSource;
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate hiveJdbcExcuetor(@Qualifier("mysqlHikariData") DataSource hiveHikariDataSource) {
        ContextDatabase.setAppSchema(DataSourceDriverHelper.getDatasourceType(url));
        ContextDatabase.setAppCatalog(DataSourceDriverHelper.getCatalog(url));
        return new JdbcTemplate(hiveHikariDataSource);
    }

}
