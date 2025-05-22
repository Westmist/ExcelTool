package org.mingxuan.util;


import org.mingxuan.constant.Mark;
import org.mingxuan.meta.Meta;
import org.mingxuan.meta.OutputType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 写文件工具
 */
public class WriteFileUtils {

    /**
     * 写文件
     */
    public static void writeJsonFile(Meta meta, Path jsonOutputPath) {
        if (!meta.getOutputTypeSet().contains(OutputType.S)) {
            return;
        }
        String name = StringUtil.removeMetaSuffix(meta.getMetaName()) + Mark.JSON;
        Path filePath = jsonOutputPath.resolve(name);
        try {
            Files.writeString(filePath, meta.toJson());
        } catch (IOException e) {
            System.out.println("导出" + name + "失败！ Exception：" + e);
        }
        System.out.println("导出" + name + "完成！");
    }


}
