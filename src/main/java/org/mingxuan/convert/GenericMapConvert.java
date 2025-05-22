package org.mingxuan.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GenericMapConvert<K, V> implements IConvert<Map<K, V>> {

    private final Function<String, K> keyParser;
    private final Function<String, V> valueParser;

    public static final IConvert<Map<Integer, Integer>> INT_INT_MAP_CONVERT =
            new GenericMapConvert<>(Integer::parseInt, Integer::parseInt);

    public static final IConvert<Map<Integer, String>> INT_STR_MAP_CONVERT =
            new GenericMapConvert<>(Integer::parseInt, s -> s);

    public static final IConvert<Map<String, Integer>> STR_INT_MAP_CONVERT =
            new GenericMapConvert<>(s -> s, Integer::parseInt);

    public static final IConvert<Map<String, String>> STR_STR_MAP_CONVERT =
            new GenericMapConvert<>(s -> s, s -> s);

    public GenericMapConvert(Function<String, K> keyParser, Function<String, V> valueParser) {
        this.keyParser = keyParser;
        this.valueParser = valueParser;
    }

    @Override
    public Map<K, V> convert(String value) {
        Map<K, V> map = new HashMap<>();
        if (value == null || value.isEmpty()) {
            return map;
        }
        String[] entries = value.split(",");
        for (String entry : entries) {
            String[] kv = entry.trim().split("-");
            if (kv.length != 2) {
                throw new IllegalArgumentException("Invalid map entry: " + entry);
            }
            K key = keyParser.apply(kv[0].trim());
            V val = valueParser.apply(kv[1].trim());
            map.put(key, val);
        }
        return map;
    }
}
