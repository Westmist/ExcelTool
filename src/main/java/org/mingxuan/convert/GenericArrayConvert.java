package org.mingxuan.convert;

import java.util.function.Function;
import java.util.function.IntFunction;

public class GenericArrayConvert<T> implements IConvert<T[]> {

    private final Function<String, T> elementParser;
    private final IntFunction<T[]> arrayFactory;

    // Array converters 缓存
    public static final IConvert<String[]> STRING_ARRAY_CONVERT =
            new GenericArrayConvert<>(s -> s, String[]::new);

    public static final IConvert<Integer[]> INTEGER_ARRAY_CONVERT =
            new GenericArrayConvert<>(Integer::parseInt, Integer[]::new);

    public GenericArrayConvert(Function<String, T> elementParser, IntFunction<T[]> arrayFactory) {
        this.elementParser = elementParser;
        this.arrayFactory = arrayFactory;
    }

    @Override
    public T[] convert(String value) {
        if (value == null || value.isEmpty()) {
            return arrayFactory.apply(0);
        }
        String[] parts = value.split(",");
        T[] result = arrayFactory.apply(parts.length);
        for (int i = 0; i < parts.length; i++) {
            result[i] = elementParser.apply(parts[i].trim());
        }
        return result;
    }
}

