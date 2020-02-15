package org.poem;

import org.poem.utils.EnumDataType;

/**
 * @author poem
 */
public class ThreadEnumDataType {

    private static ThreadLocal<EnumDataType> sourceSchema = new ThreadLocal<>();

    public static EnumDataType getSourceSchema() {
        return sourceSchema.get();
    }

    public static void setSourceSchema(EnumDataType sourceSchema) {
        ThreadEnumDataType.sourceSchema.set(sourceSchema);
    }
}
