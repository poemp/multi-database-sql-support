package org.poem;

import org.mybatis.spring.annotation.MapperScan;
import org.poem.config.mysql.MysqlDataSource;
import org.poem.entity.TTaskDetail;
import org.poem.service.TGlobalSqlVariablesService;
import org.poem.service.TTaskDetailService;
import org.poem.utils.ContextDatabase;
import org.poem.utils.DataSourceDriverHelper;
import org.poem.utils.EnumDataType;
import org.poem.utils.GlobalSqlVariablesUtils;
import org.poem.vo.GlobalSqlVariablesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author poem
 */
@SpringBootApplication
@MapperScan("org.poem.mapper")
public class MultiDatabaseSqlSupportApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MultiDatabaseSqlSupportApplication.class);

    @Autowired
    private TTaskDetailService tTaskDetailService;

    @Autowired
    private TGlobalSqlVariablesService globalSqlVariablesService;

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlDataSource;


    @Autowired
    @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate postgresDataSource;


    /**
     * start spring  boot  application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MultiDatabaseSqlSupportApplication.class, args);
    }


    /**
     * started and run
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        Map<String, GlobalSqlVariablesVO> allGlobalSqlVariableList = globalSqlVariablesService.getAllGlobalSqlVariableList();
        List<TTaskDetail> detailList = tTaskDetailService.list();
        int index = 1;
        for (TTaskDetail tTaskDetail : detailList.subList(index, index + 1)) {
            executorMysql(tTaskDetail, allGlobalSqlVariableList);
            executorPostgres(tTaskDetail, allGlobalSqlVariableList);
        }
    }

    /**
     * msyql的验证
     *
     * @param tTaskDetail              数据的sql
     * @param allGlobalSqlVariableList 方法
     */
    private void executorMysql(TTaskDetail tTaskDetail, Map<String, GlobalSqlVariablesVO> allGlobalSqlVariableList) {
        ThreadEnumDataType.setSourceSchema(EnumDataType.MYSQL);
        String sql = GlobalSqlVariablesUtils.sqlVariableManage(tTaskDetail.getExecSql(), allGlobalSqlVariableList);
        logger.info("================================ Mysql [" + tTaskDetail.getId() + "] ============================================");
        logger.info("\n" + sql);
        logger.info("================================ Mysql [" + tTaskDetail.getId() + "] ============================================");
        logger.info("");
        logger.info("");
        logger.info("");
        List<Map<String, Object>> rs = mysqlDataSource.queryForList(sql);
        logger.info("[Mysql] - " + rs.size());
    }


    /**
     * postgres的验证
     *
     * @param tTaskDetail              数据的sql
     * @param allGlobalSqlVariableList 方法
     */
    private void executorPostgres(TTaskDetail tTaskDetail, Map<String, GlobalSqlVariablesVO> allGlobalSqlVariableList) {
        ThreadEnumDataType.setSourceSchema(EnumDataType.POSTGRES);
        String sql = GlobalSqlVariablesUtils.sqlVariableManage(tTaskDetail.getExecSql(), allGlobalSqlVariableList);
        logger.info("================================ Postgres [" + tTaskDetail.getId() + "] ============================================");
        logger.info("\n" + sql);
        logger.info("================================ Postgres [" + tTaskDetail.getId() + "] ============================================");
        logger.info("");
        logger.info("");
        logger.info("");
        List<Map<String, Object>> rs = postgresDataSource.queryForList(sql);
        logger.info("[Postgres] - " + rs.size());
    }
}
