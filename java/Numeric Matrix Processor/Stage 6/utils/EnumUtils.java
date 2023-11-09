package org.example.utils;

import java.util.function.Function;

public class EnumUtils {
    public static <T> T getEnumValues(T[] values, Function<T, Boolean> equalityComparison) {
        for (T option : values) {
            if (equalityComparison.apply(option)) return option;
        }

        throw new IllegalArgumentException();
    }
}
