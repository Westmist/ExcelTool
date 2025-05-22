package org.mingxuan.config;

import lombok.Data;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.List;

@Data
public class AppConfig {

    public static final AppConfig CONFIG;

    static {
        Constructor constructor = new Constructor(AppConfig.class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);
        InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("config.yml");
        if (input == null) {
            throw new RuntimeException("配置文件未找到");
        }
        CONFIG = yaml.load(input);
    }

    private Path path;
    private Head head;
    private Excel excel;
    private Json json;

    @Data
    public static class Path {
        private String input;
        private String metaOutput;
        private String jsonOutput;
    }

    @Data
    public static class Head {
        private String packageName;
        private List<String> imps;
    }

    @Data
    public static class Excel {
        /**
         * 表中主键字段名
         */
        private String snName;
        private List<String> vertical;
    }

    @Data
    public static class Json {
        private boolean pretty;
    }
}

