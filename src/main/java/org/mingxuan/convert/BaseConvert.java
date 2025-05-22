package org.mingxuan.convert;

public class BaseConvert {

    public static class IntConvert implements IConvert<Integer> {
        public Integer convert(String value) {
            return Integer.parseInt(value);
        }
    }

    public static class LongConvert implements IConvert<Long> {
        public Long convert(String value) {
            return Long.parseLong(value);
        }
    }

    public static class FloatConvert implements IConvert<Float> {
        public Float convert(String value) {
            return Float.parseFloat(value);
        }
    }

    public static class DoubleConvert implements IConvert<Double> {
        public Double convert(String value) {
            return Double.parseDouble(value);
        }
    }

    public static class BooleanConvert implements IConvert<Boolean> {
        public Boolean convert(String value) {
            return Boolean.parseBoolean(value);
        }
    }

    public static class ByteConvert implements IConvert<Byte> {
        public Byte convert(String value) {
            return Byte.parseByte(value);
        }
    }

    public static class ShortConvert implements IConvert<Short> {
        public Short convert(String value) {
            return Short.parseShort(value);
        }
    }

    public static class CharConvert implements IConvert<Character> {
        public Character convert(String value) {
            if (value.length() != 1) {
                throw new IllegalArgumentException("Cannot convert to char: " + value);
            }
            return value.charAt(0);
        }
    }

    public static class StringConvert implements IConvert<String> {
        public String convert(String value) {
            return value;
        }
    }

}
