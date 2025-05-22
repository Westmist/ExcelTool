package org.mingxuan.util;

import org.mingxuan.constant.Mark;

public class StringUtil {
    public static String metaName(String sheetName) {
        if (sheetName == null || sheetName.isEmpty()) {
            return sheetName;
        }
        return sheetName.substring(0, 1).toUpperCase() + sheetName.substring(1) + Mark.META;
    }

    public static String removeMetaSuffix(String metaStr) {
        if (metaStr != null && metaStr.endsWith(Mark.META)) {
            return metaStr.substring(0, metaStr.length() - 4);
        }
        return metaStr;
    }
}
