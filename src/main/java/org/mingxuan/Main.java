package org.mingxuan;


import org.apache.commons.io.FileUtils;
import org.mingxuan.config.AppConfig;
import org.mingxuan.meta.Meta;
import org.mingxuan.util.ReadExcelUtils;
import org.mingxuan.util.TemplateUtils;
import org.mingxuan.util.WriteFileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        AppConfig.Path path = AppConfig.CONFIG.getPath();
        String inputStr = path.getInput();
        Path inputPath = Path.of(inputStr);

        Path metaOutputPath = Path.of(path.getMetaOutput());
        Path jsonOutputPath = Path.of(path.getJsonOutput());

        //清理导出目录
        FileUtils.cleanDirectory(metaOutputPath.toFile());
        FileUtils.cleanDirectory(jsonOutputPath.toFile());

        try (Stream<Path> pathStream = Files.find(inputPath, Integer.MAX_VALUE, ReadExcelUtils::filterFile)) {
            //解析excel
            Stream<Meta> metaStream = pathStream.flatMap(ReadExcelUtils::readExcel);

            metaStream.forEach((meta -> {
                // 导出 json 文件
                WriteFileUtils.writeJsonFile(meta, jsonOutputPath);
                // 导出模板生成文件
                TemplateUtils.writeFile(meta, metaOutputPath);
            }));

        }

    }
}