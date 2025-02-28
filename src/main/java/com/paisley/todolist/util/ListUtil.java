package com.paisley.todolist.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ListUtil
 *
 * @author Sero
 * @since 2009/2/16
 **/
public final class ListUtil {

    private ListUtil() {}

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> syncList() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    @SafeVarargs
    public static <T> List<T> as(T... element) {
        if (element.length == 1) {
            return Collections.singletonList(element[0]);
        }
        return Arrays.stream(element).collect(Collectors.toList());
    }

    /**
     * List分組，每listSize筆分一組
     *
     * @param list list
     * @param listSize listSize
     * @return List<List<T>>
     * @param <T> T
     */
    public static <T> List<List<T>> limit(List<T> list, int listSize) {
        long limit = (list.size() + listSize - 1) / listSize;
        return Stream.iterate(0, i -> i + 1)
                .limit(limit)
                .map(i -> list.stream().skip((long) i * listSize).limit(listSize)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

}
