package com.paisley.todolist.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static java.util.Objects.nonNull;

/**
 * TypesUtil
 *
 * @author Sero
 * @since 2009/2/16
 **/
public final class TypesUtil {

    private TypesUtil() {}

    // Regular expression for scientific notation.
    private static final String SCIENTIFIC_NOTATION_REGEX = "[+-]?\\d[.]?\\d*[Ee][+-]?\\d+";
    // Regular expression for numbers.
    private static final String NUMBER_PATTERN_REGEX = "^[+-]?\\d+(\\.\\d+)?$";

    /**
     * 轉換字串，若為null則不轉換
     *
     * @param object object
     * @return String
     */
    public static String parseStr(Object object) {
        if (object == null || typeInvalid(object))
            return null;
        String result = String.valueOf(object);
        // Prevent scientific notation issues through self-conversion.
        if (object instanceof BigDecimal || result.matches(SCIENTIFIC_NOTATION_REGEX)) {
            BigDecimal resultBd = object instanceof BigDecimal bigDecimal ? bigDecimal : new BigDecimal(result);
            result = resultBd.toPlainString();
        } else {
            result = String.valueOf(object);
        }
        return result;
    }

    /**
     * 轉換BigDecimal，若為null或非數字格式則不轉換
     *
     * @param object object
     * @return BigDecimal
     */
    public static BigDecimal parseDecimal(Object object) {
        if (object == null || typeInvalid(object))
            return null;
        BigDecimal bigDecimal = null;
        if (object instanceof Number || object instanceof String) {
            String str = StringUtil.defaultBlank(parseStr(object), StringUtil.EMPTY);
            if (str.matches(NUMBER_PATTERN_REGEX)) {
                bigDecimal = new BigDecimal(str);
            }
        }
        return bigDecimal;
    }

    /**
     * 轉換Integer，若為null或非整數格式則不轉換
     *
     * @param object object
     * @return Integer
     */
    public static Integer parseInteger(Object object) {
        if (object == null || typeInvalid(object)) {
            return null;
        }
        if (object instanceof BigDecimal begDecimal) {
            return begDecimal.intValue();
        }

        Integer integer = null;
        String str = StringUtil.defaultBlank(parseStr(object), StringUtil.EMPTY);
        if (str.matches("[+-]?\\d+")) {
            integer = Integer.parseInt(str);
        }
        return integer;
    }

    /**
     * 轉換數字格式物件為字串(前端補零)，若為null或非數字格式則不轉換
     *
     * @param object       原始數字格式物件
     * @param formatLength 總長度
     * @return String
     */
    public static String parseStrZeroPadding(Object object, int formatLength) {
        String str = null;
        Integer integer = parseInteger(object);
        if (integer != null) {
            String format = "%0" + formatLength + "d";
            str = String.format(format, integer);
        }
        return str;
    }

    /**
     * 轉換數字，若為null或非Number則給預設值
     *
     * @param number number
     * @param defaultValue defaultValue
     * @param parseClass parseClass
     * @param <T> T
     * @param <U> U
     * @return parseNumber
     */
    public static <T, U> U parseNumber(T number, U defaultValue, Class<U> parseClass) {
        U result = defaultValue;
        String numStr = StringUtil.defaultBlank(parseStr(number), StringUtil.EMPTY);
        if (number instanceof Number || numStr.matches(NUMBER_PATTERN_REGEX)) {
            BigDecimal numDecimal = new BigDecimal(numStr);
            result = switch (parseClass.getSimpleName()) {
                case "Integer" -> parseClass.cast(numDecimal.intValue());
                case "Double" -> parseClass.cast(numDecimal.doubleValue());
                case "Long" -> parseClass.cast(numDecimal.longValue());
                case "Float" -> parseClass.cast(numDecimal.floatValue());
                case "Short" -> parseClass.cast(numDecimal.shortValue());
                case "BigInteger" -> parseClass.cast(numDecimal.toBigInteger());
                default -> defaultValue;
            };
        }
        return result;
    }

    /**
     * 千分位格式化數字格式物件
     *
     * @param object object
     * @return String
     */
    public static String thousandsFormat(Object object) {
        String str = null;
        BigDecimal bigDecimal = parseDecimal(object);
        if (nonNull(bigDecimal)) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            str = numberFormat.format(bigDecimal);
        }
        return str;
    }

    /**
     * 千分位格式化數字格式物件(指定格式)
     *
     * @param object object
     * @param pattern 格式化規則
     * @return String
     */
    public static String thousandsFormat(Object object, String pattern) {
        String str = null;
        BigDecimal bigDecimal = parseDecimal(object);
        if (nonNull(bigDecimal)) {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            str = decimalFormat.format(bigDecimal);
        }
        return str;
    }

    private static boolean typeInvalid(Object object) {
        Class<?> parseClass = object.getClass();
        return switch (parseClass.getSimpleName()) {
            case "String", "Byte", "Integer", "Double", "Long", "Float", "Short", "BigDecimal", "BigInteger" -> false;
            default -> true;
        };
    }

}
