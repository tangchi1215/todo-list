package com.paisley.todolist.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MapUtil
 *
 * @author Sero
 * @since 2009/2/16
 **/
public final class MapUtil {

    private MapUtil() {}

    public static <T, V> Map<T, V> newHashMap() {
        return new HashMap<>();
    }

    public static <T, U> Map<T, U> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <T, U> Map<T, U> of(T key, U value) {
        Map<T, U> dataMap = newHashMap();
        dataMap.put(key, value);
        return dataMap;
    }

    public static <T, U> Map<T, U> of(T key1, U value1, T key2, U value2) {
        Map<T, U> dataMap = newHashMap();
        dataMap.put(key1, value1);
        dataMap.put(key2, value2);
        return dataMap;
    }

    public static <T, U> Map<T, U> of(T key1, U value1, T key2, U value2, T key3, U value3) {
        Map<T, U> dataMap = newHashMap();
        dataMap.put(key1, value1);
        dataMap.put(key2, value2);
        dataMap.put(key3, value3);
        return dataMap;
    }

    /**
     * get T with default
     *
     * @param map map
     * @param key key
     * @return T
     * @param <T> T
     */
    public static <T> T get(Map<String, T> map, String key, T defaultValue) {
        return map == null ? defaultValue : map.computeIfAbsent(key, k -> defaultValue);
    }

    /**
     * get T
     *
     * @param map map
     * @param key key
     * @return T|null
     * @param <T> T
     */
    public static <T> T get(Map<String, T> map, String key) {
        return map == null ? null : map.computeIfAbsent(key, k -> null);
    }

    /**
     * get with string
     *
     * @param map map
     * @param key key
     * @return String
     */
    public static String getString(Map<String, Object> map, String key) {
        return (String) get(map, key);
    }

    /**
     * get U with cast
     *
     * @param map map
     * @param key key
     * @return T|null
     * @param <T> T
     */
    public static <T, U> U getCast(Map<String, T> map, String key, Class<U> uClass) {
        return map == null ? null : uClass.cast(map.computeIfAbsent(key, k -> null));
    }

}
