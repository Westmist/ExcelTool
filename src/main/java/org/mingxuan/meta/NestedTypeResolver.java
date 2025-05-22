package org.mingxuan.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedTypeResolver {

    private static final Map<String, String> PRIMITIVE_MAP = Map.of(
            "int", "Integer",
            "long", "Long",
            "float", "Float",
            "double", "Double",
            "bool", "Boolean",
            "boolean", "Boolean",
            "byte", "Byte",
            "short", "Short",
            "char", "Character",
            "string", "String"
    );

    public static String resolve(String typeStr) {
        typeStr = typeStr.trim();
        if (typeStr.endsWith("[]")) {
            return toJavaType(typeStr.substring(0, typeStr.length() - 2)) + "[]";
        } else if (typeStr.startsWith("map[")) {
            List<String> parts = parseBracketContent(typeStr.substring(4, typeStr.length() - 1));
            if (parts.size() != 2) throw new IllegalArgumentException("Invalid map format: " + typeStr);
            return "Map<" + resolve(parts.get(0)) + ", " + resolve(parts.get(1)) + ">";
        } else if (typeStr.startsWith("array[")) {
            String inner = typeStr.substring(6, typeStr.length() - 1);
            return "List<" + resolve(inner) + ">";
        } else {
            return toJavaType(typeStr);
        }
    }

    private static String toJavaType(String type) {
        return PRIMITIVE_MAP.getOrDefault(type, type);
    }

    private static List<String> parseBracketContent(String content) {
        List<String> result = new ArrayList<>();
        int level = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : content.toCharArray()) {
            if (c == ',' && level == 0) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                if (c == '[') level++;
                if (c == ']') level--;
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result;
    }

}
