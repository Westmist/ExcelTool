package org.mingxuan.meta;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class Field {
    /**
     * 索引从 0 开始
     */
    private int index;

    /**
     * 字段名
     */
    private String name;
    /**
     * 字段描述
     */
    private String describe;
    /**
     * 数据类型
     */
    private FieldType dataType;
    /**
     * 导出类型
     */
    private Set<OutputType> outputTypeSet = new HashSet<>();
    /**
     * 默认值
     */
    private String defaultValue;

}
