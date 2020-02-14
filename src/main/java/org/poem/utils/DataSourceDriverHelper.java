package org.poem.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author sangfor
 */
public class DataSourceDriverHelper {

    /**
     * 根据数据源的信息，返回数据库的驱动信息
     *
     * @param url
     * @return
     */
    public static String getJdbc(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        } else if (url.startsWith("jdbc:mysql")) {
            return "com.mysql.cj.jdbc.Driver";
        } else if (url.startsWith("jdbc:hive2")) {
            return "org.apache.hive.jdbc.HiveDriver";
        } else if (url.startsWith("jdbc:oracle:thin")) {
            return "oracle.jdbc.driver.OracleDriver";
        } else if (url.startsWith("jdbc:postgresql")) {
            return "org.postgresql.Driver";
        }

        return "";
    }

    /**
     * 数据库
     *
     * @param url
     * @return
     */
    public static String getCatalog(String url) {
        int idex = url.lastIndexOf("?");
        char[] chars = url.toCharArray();
        char x = "/".toCharArray()[0];
        for (int i = idex; i >= 0; i--) {
            if (chars[i] == x) {
                return url.substring(i + 1, idex);
            }
        }
        return "";
    }

    /**
     * 根据数据源的信息，返回数据库的驱动信息
     *
     * @param url
     * @return
     */
    public static EnumDataType getDatasourceType(String url) {
        if (StringUtils.isEmpty(url)) {
            return EnumDataType.NULL;
        } else if (url.startsWith("jdbc:mysql")) {
            return EnumDataType.MYSQL;
        } else if (url.startsWith("jdbc:hive2")) {
            return EnumDataType.HIVE;
        } else if (url.startsWith("jdbc:oracle:thin")) {
            return EnumDataType.ORACLE;
        } else if (url.startsWith("jdbc:postgresql")) {
            return EnumDataType.POSTGRES;
        }

        return EnumDataType.NULL;
    }
}
