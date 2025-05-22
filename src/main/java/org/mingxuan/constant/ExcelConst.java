package org.mingxuan.constant;

public interface ExcelConst {

    interface HORIZONTAL_INDEX {
        /**
         * 导出类型
         */
        int OUT_TYPE_ROW = 0;

        /**
         * 数据类型
         */
        int DATA_TYPE_ROW = 1;

        /**
         * 字段名字
         */
        int DATA_NAME_ROW = 2;

        /**
         * 字段描述
         */
        int DATA_DESC_ROW = 3;

        /**
         * 第一行数据
         */
        int H_DATA_FIRST_ROW = 4;
    }

    interface VERTICAL_INDEX {

        /**
         * 字段名字
         */
        int DATA_NAME_COL = 0;

        /**
         * 数据类型
         */
        int DATA_TYPE_COL = 1;

        /**
         * 数据值
         */
        int DATA_VALUE_COL = 2;

        /**
         * 字段描述
         */
        int DATA_DESC_COL = 3;

        /**
         * 第一行数据
         */
        int V_DATA_FIRST_ROW = 4;
    }


}
