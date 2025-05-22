package org.mingxuan.meta;

import lombok.Getter;
import org.mingxuan.convert.*;

import java.util.List;
import java.util.Map;

/**
 * 字段类型
 */
@Getter
public enum FieldType {

    INT("int", new BaseConvert.IntConvert()),
    LONG("long", new BaseConvert.LongConvert()),
    FLOAT("float", new BaseConvert.FloatConvert()),
    DOUBLE("double", new BaseConvert.DoubleConvert()),
    BOOLEAN("bool", new BaseConvert.BooleanConvert()),
    BYTE("byte", new BaseConvert.ByteConvert()),
    SHORT("short", new BaseConvert.ShortConvert()),
    CHAR("char", new BaseConvert.CharConvert()),
    STR("string", new BaseConvert.StringConvert()),

    STR_ARR("string[]", GenericArrayConvert.STRING_ARRAY_CONVERT),

    INT_ARR("int[]", GenericArrayConvert.INTEGER_ARRAY_CONVERT),


    NT_LIST("list[int]", GenericListConvert.INT_LIST_CONVERT),

    INT_INT_MAP("map[int,int]", GenericMapConvert.INT_INT_MAP_CONVERT),
    INT_STR_MAP("map[int,string]", GenericMapConvert.INT_STR_MAP_CONVERT),
    STR_INT_MAP("map[string,int]", GenericMapConvert.STR_INT_MAP_CONVERT),
    STR_STR_MAP("map[string,string]", GenericMapConvert.STR_STR_MAP_CONVERT),

    ;


    /**
     * 表中配置的类型
     */
    private final String typeName;

    /**
     * 导出的java类型
     */
    private final String javaTypeName;

    private final IConvert<?> convert;

    FieldType(String typeName, IConvert<?> convert) {
        this.typeName = typeName;
        this.convert = convert;
        this.javaTypeName = NestedTypeResolver.resolve(this.typeName);
    }

    public static FieldType getType(String name) {
        name = name.trim().toLowerCase();
        for (FieldType value : values()) {
            if (value.typeName.equals(name)) {
                return value;
            }
        }
        return STR;
    }

}
