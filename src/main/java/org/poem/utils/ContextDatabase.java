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

    public static EnumDataType getSourceSchema() {
        return sourceSchema;
    }

    public static void setSourceSchema(EnumDataType sourceSchema) {
        ContextDatabase.sourceSchema = sourceSchema;
    }

}
