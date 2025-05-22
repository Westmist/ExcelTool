package org.mingxuan.meta;

import java.util.HashSet;
import java.util.Set;

public enum OutputType {
    /**
     * 客户端
     */
    C,
    /**
     * 服务器
     */
    S,
    /**
     * 策划描述型不需要
     */
    N;

    public static Set<OutputType> of(String str) {
        Set<OutputType> set = new HashSet<>(3);
        for (OutputType outputType : OutputType.values()) {
            if (!str.toUpperCase().contains(outputType.toString())) {
                continue;
            }
            set.add(outputType);
        }
        return set;
    }

    public boolean included(Set<OutputType> outputTypeSet) {
        return outputTypeSet.contains(this);
    }

}
