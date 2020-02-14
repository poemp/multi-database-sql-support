package org.poem;

import org.mybatis.spring.annotation.MapperScan;
import org.poem.entity.TTaskDetail;
import org.poem.service.TGlobalSqlVariablesService;
import org.poem.service.TTaskDetailService;
import org.poem.utils.ContextDatabase;
import org.poem.utils.DataSourceDriverHelper;
import org.poem.utils.GlobalSqlVariablesUtils;
import org.poem.vo.GlobalSqlVariablesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    @Value("${spring.datasource.url}")
    private String url;

    /**
     * start spring  boot  application
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MultiDatabaseSqlSupportApplication.class, args);
    }


    /**
     * started and run
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        ContextDatabase.setSourceSchema(DataSourceDriverHelper.getDatasourceType(url));
        Map<String, GlobalSqlVariablesVO> allGlobalSqlVariableList = globalSqlVariablesService.getAllGlobalSqlVariableList();
        List<TTaskDetail> detailList = tTaskDetailService.list();
        for (TTaskDetail tTaskDetail : detailList) {
            String sql = GlobalSqlVariablesUtils.sqlVariableManage(tTaskDetail.getExecSql(), allGlobalSqlVariableList);
            logger.info("================================ [" + tTaskDetail.getId() + "] ============================================");
            logger.info(sql);
            logger.info("================================ [" + tTaskDetail.getId() + "] ============================================");
            logger.info("");
            logger.info("");
            logger.info("");
        }
    }
}
