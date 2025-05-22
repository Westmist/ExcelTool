package org.mingxuan.meta;

import org.mingxuan.config.AppConfig;

import java.util.function.Consumer;

import static org.mingxuan.constant.Mark.META_H_FTL;
import static org.mingxuan.constant.Mark.META_V_FTL;


public enum MetaType {
    /**
     * 横表
     */
    HORIZONTAL(MetaTransition::horizontal, META_H_FTL),
    /**
     * 竖表
     */
    VERTICAL(MetaTransition::vertical, META_V_FTL),
    ;

    private final Consumer<Meta> consumer;

    private final String ftlName;

    MetaType(Consumer<Meta> consumer, String ftlName) {
        this.consumer = consumer;
        this.ftlName = ftlName;
    }

    public String ftlName() {
        return ftlName;
    }

    public void genMeta(Meta meta) {
        consumer.accept(meta);
    }

    public static MetaType type(String excelName) {
        return AppConfig.CONFIG.getExcel().getVertical().contains(excelName) ? VERTICAL : HORIZONTAL;
    }

}
