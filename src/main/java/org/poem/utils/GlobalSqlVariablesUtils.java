package org.poem.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Maps;
import org.poem.ThreadEnumDataType;
import org.poem.vo.GlobalSqlVariablesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GlobalSqlVariablesUtils {

    private static final Logger logger = LoggerFactory.getLogger(GlobalSqlVariablesUtils.class);
    /**
     * 替换方法
     */
    private static Pattern regex = Pattern.compile("&\\{([^}]*)\\}");

    public static void main(String[] args) {
        ContextDatabase.setSourceSchema(EnumDataType.POSTGRES);
        ThreadEnumDataType.setSourceSchema(EnumDataType.MYSQL);
        String sql = "SELECT  &{case_number(@●prizeName●)} as prize FROM lottery_win_prize ORDER BY ●createTime● ";
        System.out.println(sql);
        System.out.println("\n\n");
        System.out.println(sqlVariableManage(sql, new HashMap<String, GlobalSqlVariablesVO>() {{
            GlobalSqlVariablesVO variablesVO = new GlobalSqlVariablesVO();
            variablesVO.setFunctionName("case_number");
            variablesVO.setMysqlFunction("CAST(@ as unsigned)");
            variablesVO.setHiveFunction("");
            variablesVO.setPostgresFunction("substring(@, '[0-9]*')");
            put("case_number", variablesVO);

        }}));
    }

    /**
     * @param sql
     * @return
     */
    private static Map<String, String> getVariables(String sql) {
        Map<String, String> stringList = Maps.newHashMap();
        Matcher matcher = regex.matcher(sql);
        while (matcher.find()) {
            stringList.put(matcher.group(0), matcher.group(1));
        }
        return stringList;
    }

    /**
     * 解析变量名字
     *
     * @param sqlVariable 变量
     * @return
     */
    private static String getFunctionName(String sqlVariable) {
        int index = sqlVariable.indexOf("(");
        if (index != -1) {
            return sqlVariable.substring(0, index);
        }
        return sqlVariable;
    }

    /**
     * 获取参数
     *
     * @param sqlVariable  变量
     * @param functionName 方法名字
     * @return
     */
    private static String getParams(String sqlVariable, String functionName) {
        if (sqlVariable.contains("@")) {
            String params = sqlVariable
                    .replaceAll("@", "")
                    .replaceAll(functionName, "");
            return params.substring(1, params.length() - 1);
        }else if (sqlVariable.contains("(")){
            return sqlVariable.substring(sqlVariable.indexOf("(")+1, sqlVariable.length() -1);
        }
        return "";
    }

    /**
     * 替换变量成sql
     *
     * @param content
     * @param kvs
     * @return
     */
    private static String parse(String content, Map<String, String> kvs) {
        Matcher m = regex.matcher(content);
        StringBuffer sr = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            if (kvs.get(group) == null){
                logger.warn("{} The Key Is Null , Please Add In Global Sql Variable",sr );
            }else {
                m.appendReplacement(sr, kvs.get(group));
            }
        }
        m.appendTail(sr);
        return replaceSpecialCharacter(sr.toString());
    }

    /**
     * 替换特殊字符
     * @param originSql
     * @return
     */
    private static String replaceSpecialCharacter(String originSql){
        EnumDataType enumDataType = ThreadEnumDataType.getSourceSchema();
        switch (enumDataType.getType().toLowerCase()) {
            case "hive":
                return originSql.replaceAll("●", "");
            case "hbase":
                return originSql.replaceAll("●", "");
            case "postgres":
                return originSql.replaceAll("●", "\"");
            default:
                return  originSql.replaceAll("●", "");
        }
    }

    /**
     * 获取数据的函数类型
     *
     * @return
     */
    private static String getSchema(GlobalSqlVariablesVO globalSqlVariablesVO) {
        EnumDataType enumDataType = ThreadEnumDataType.getSourceSchema();
        switch (enumDataType.getType()) {
            case "hive":
                return globalSqlVariablesVO.getHiveFunction();
            case "hbase":
                return globalSqlVariablesVO.getHbaseFunction();
            case "postgres":
                return globalSqlVariablesVO.getPostgresFunction();
            default:
                return  globalSqlVariablesVO.getMysqlFunction();
        }
    }

    /**
     * 替换变量
     *
     * @param sql            需要替换的sql
     * @param variablesVOMap 变量值管理
     * @return
     */
    public static String sqlVariableManage(String sql, Map<String, GlobalSqlVariablesVO> variablesVOMap) {
        Map<String, String> variables = getVariables(sql);
        Map<String, String> newVariables = Maps.newHashMap();
        variables.forEach(
                (k, sqlVariable) -> {
                    String functionName = getFunctionName(sqlVariable);
                    String par = getParams(sqlVariable, functionName);
                    GlobalSqlVariablesVO variablesVO = variablesVOMap.get(functionName);
                    if (variablesVO != null) {
                        String mysqlFunction = getSchema(variablesVO);
                        if (StringUtils.isNotEmpty(mysqlFunction) && mysqlFunction.contains("@")) {
                            String newFunctionName = mysqlFunction.replaceAll("@", par);
                            newVariables.put(k, newFunctionName);
                        }else{
                            if (StringUtils.isNotEmpty(mysqlFunction) && !mysqlFunction.contains("@")) {
                                String newFunctionName = k.replaceAll("&\\{"+ functionName, mysqlFunction).replaceAll("}","");
                                newVariables.put(k, newFunctionName);
                            }
                        }
                    }
                }
        );
        return parse(sql, newVariables);
    }

}
