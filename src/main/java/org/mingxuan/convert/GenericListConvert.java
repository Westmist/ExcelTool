package org.mingxuan.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GenericListConvert<T> implements IConvert<List<T>> {

    private final Function<String, T> elementParser;

    public static final IConvert<List<Integer>> INT_LIST_CONVERT =
            new GenericListConvert<>(Integer::parseInt);

    public GenericListConvert(Function<String, T> elementParser) {
        this.elementParser = elementParser;
    }

    @Override
    public List<T> convert(String value) {
        List<T> list = new ArrayList<>();
        if (value == null || value.isEmpty()) {
            return list;
        }
        String[] parts = value.split(",");
        for (String part : parts) {
            list.add(elementParser.apply(part.trim()));
        }
        return list;
    }
}
