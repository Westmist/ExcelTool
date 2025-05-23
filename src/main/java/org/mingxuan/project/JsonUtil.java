package org.mingxuan.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.mingxuan.config.AppConfig;

import java.io.Reader;

public class JsonUtil {

    private static Gson GSON;

    static {
        if (AppConfig.CONFIG.getJson().isPretty()) {
            GSON = new GsonBuilder().setPrettyPrinting().create();
        } else {
            GSON = new Gson();
        }
    }

    /**
     * 将对象转换为json
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * 将对象写为json文件
     */
    public static void writerJson(Object obj, Appendable writer) {
        GSON.toJson(obj, writer);
    }

    /**
     * 将json转换为对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * 将json转换为对象
     */
    public static <T> T toObject(JsonElement json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * 将json文件读取为对象
     */
    public static <T> T readObject(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

}
