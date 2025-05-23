package org.mingxuan.project;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.mingxuan.Main;
import org.mingxuan.config.AppConfig;
import org.mingxuan.util.StringUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MetaLoad {
    public static void load(Path jsonDirPath, String packageName) {
        // 读取所有 JSON 文件
        Map<String, String> classJsonMap = new HashMap<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(jsonDirPath, "*.json")) {
            for (Path path : stream) {
                String fileName = path.getFileName().toString();
                String className = fileName.substring(0, fileName.length() - 5);
                String json;
                try {
                    json = Files.readString(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                classJsonMap.put(className, json);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {
            scanResult.getAllClasses().forEach(classInfo -> {
                try {
                    Class<?> clazz = classInfo.loadClass();
                    Method method = clazz.getDeclaredMethod("load", String.class);
                    if (Modifier.isStatic(method.getModifiers())) {
                        String name = StringUtil.removeMetaSuffix(clazz.getSimpleName());
                        String json = classJsonMap.get(name);
                        if (json == null) {
                            System.out.println("类" + clazz.getSimpleName() + "不存在对应的json文件");
                            throw new RuntimeException();
                        }
                        method.invoke(null, json);
                        System.out.println("Executed: " + clazz.getName() + ".load()");
                    }
                } catch (NoSuchMethodException e) {
                    // 忽略没有 load 方法的类
                } catch (Exception e) {
                    System.err.println("Error invoking load for: " + classInfo.getName());
                    e.printStackTrace();
                }
            });
        }
    }
}
