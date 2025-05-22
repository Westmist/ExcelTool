package org.mingxuan.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.mingxuan.Main;
import org.mingxuan.config.AppConfig;
import org.mingxuan.constant.Mark;
import org.mingxuan.meta.Meta;
import org.mingxuan.meta.OutputType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;

/**
 * 模板工具
 */
public class TemplateUtils {

    private static final Configuration CFG = new Configuration(Configuration.VERSION_2_3_22);

    static {
        URL resource = Main.class.getClassLoader().getResource("ftl");
        if (resource == null) {
            throw new RuntimeException("资源目录 ftl 未找到！");
        }

        File ftlDir = new File(resource.getPath());
        if (!ftlDir.exists() || !ftlDir.isDirectory()) {
            throw new RuntimeException("ftl 不是一个有效目录！");
        }
        try {
            CFG.setDirectoryForTemplateLoading(ftlDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CFG.setDefaultEncoding("UTF-8");
        CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    /**
     * 获取freemarker模版
     *
     * @param fileName 模板名
     * @return 模版
     */
    public static Template getTemplate(String fileName) {
        try {
            return CFG.getTemplate(fileName);
        } catch (IOException e) {
            throw new RuntimeException("没有模板" + fileName);
        }
    }

    /**
     * 根据模板生成文件
     */
    public static void writeFile(Meta meta, Path metaOutputPath) {
        if (!meta.getOutputTypeSet().contains(OutputType.S)) {
            return;
        }
        String name = meta.getMetaName() + Mark.JAVA;
        Path filePath = metaOutputPath.resolve(name);
        try (PrintWriter pw = new PrintWriter(filePath.toFile())) {
            String serverFtl = meta.getMetaType().ftlName();
            Template serverTemplate = getTemplate(serverFtl);
            serverTemplate.process(meta, pw);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(meta.getSheetName() + "生成模板失败", e);
        }
        System.out.println("生成" + name + "完成！");
    }

}
