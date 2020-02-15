package org.poem.utils;

import lombok.Data;

/**
 * @author sangfor
 */
@Data
public class ContextDatabase {

    /**
     * 来源的库的类型
     */
    private static EnumDataType sourceSchema;

    /**
     * 数据库
     */
    private static String sourceCatalog;
    /**
     * j结果表的数据库类型
     */
    private static EnumDataType appSchema;

    /**
     * 数据库
     */
    private static String appCatalog;

    /**
     * 模块数据
     */
    private static EnumDataType modelSchema;
    /**
     * 数据库
     */
    private static String modelCatalog;

    public static EnumDataType getModelSchema() {
        return modelSchema;
    }

    public static void setModelSchema(EnumDataType modelSchema) {
        ContextDatabase.modelSchema = modelSchema;
    }

    public static EnumDataType getSourceSchema() {
        return sourceSchema;
    }

    public static void setSourceSchema(EnumDataType sourceSchema) {
        ContextDatabase.sourceSchema = sourceSchema;
    }

    public static EnumDataType getAppSchema() {
        return appSchema;
    }

    public static void setAppSchema(EnumDataType appSchema) {
        ContextDatabase.appSchema = appSchema;
    }

    public static String getSourceCatalog() {
        return sourceCatalog;
    }

    public static void setSourceCatalog(String sourceCatalog) {
        ContextDatabase.sourceCatalog = sourceCatalog;
    }

    public static String getAppCatalog() {
        return appCatalog;
    }

    public static void setAppCatalog(String appCatalog) {
        ContextDatabase.appCatalog = appCatalog;
    }

    public static String getModelCatalog() {
        return modelCatalog;
    }

    public static void setModelCatalog(String modelCatalog) {
        ContextDatabase.modelCatalog = modelCatalog;
    }

}
