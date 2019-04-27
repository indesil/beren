package io.github.indesil.beren.operations;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SequenceOperations {
    public static boolean isNotEmpty(CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }

    public static boolean size(CharSequence charSequence, int min, int max) {
        return charSequence == null || (charSequence.length() >= min && charSequence.length() <= max);
    }

    public static boolean size(Collection<?> collection, int min, int max) {
        return collection == null || (collection.size() >= min && collection.size() <= max);
    }

    public static boolean size(Map<?, ?> map, int min, int max) {
        return map == null || (map.size() >= min && map.size() <= max);
    }

    public static boolean size(Object[] array, int min, int max) {
        return array == null || (array.length >= min && array.length <= max);
    }

    public static boolean oneOf(Object input, List<Object> expectedObjects) {
        return expectedObjects.contains(input);
    }

    public static boolean neitherOf(Object input, List<Object> expectedObjects) {
        return !expectedObjects.contains(input);
    }
}
