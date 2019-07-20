package io.github.indesil.beren.operations;

import org.apache.commons.lang3.StringUtils;

public class StringOperations {

    public static boolean isNotBlank(CharSequence input) {
        return StringUtils.isNotBlank(input);
    }

    public static boolean startsWith(CharSequence input, CharSequence prefix) {
        return input == null || StringUtils.startsWith(input, prefix);
    }

    public static boolean endsWith(CharSequence input, CharSequence prefix) {
        return input == null || StringUtils.endsWith(input, prefix);
    }

    public static boolean contains(CharSequence input, CharSequence prefix) {
        return input == null || StringUtils.contains(input, prefix);
    }
}