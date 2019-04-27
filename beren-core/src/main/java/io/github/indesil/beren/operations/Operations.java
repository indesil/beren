package io.github.indesil.beren.operations;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Operations {

    public static boolean oneOf(Object input, List<Object> expectedObjects) {
        return expectedObjects.contains(input);
    }

    public static boolean neitherOf(Object input, List<Object> expectedObjects) {
        return !expectedObjects.contains(input);
    }

    public static boolean greaterThan(Integer number, int a) {
        return number != null && number > a;
    }

    public static boolean greaterThan(Double number, double a) {
        return number != null && number > a;
    }

    public static boolean greaterThan(Number number, int a) {
        return number != null && number.intValue() > a;
    }

    public static boolean greaterThanOrEquals(Integer number, int a) {
        return number != null && number >= a;
    }

    public static boolean greaterThanOrEquals(Double number, double a) {
        return number != null && number >= a;
    }

    public static boolean greaterThanOrEquals(Number number, double a) {
        return number != null && number.intValue() >= a;
    }

    public static boolean lessThan(Integer number, int a) {
        return number != null && number < a;
    }

    public static boolean lessThan(Double number, double a) {
        return number != null && number < a;
    }

    public static boolean lessThan(Number number, double a) {
        return number != null && number.intValue() < a;
    }

    public static boolean lessThanOrEquals(Integer number, int a) {
        return number != null && number <= a;
    }

    public static boolean lessThanOrEquals(Double number, double a) {
        return number != null && number <= a;
    }

    public static boolean lessThanOrEquals(Number number, double a) {
        return number != null && number.intValue() <= a;
    }

    public static boolean notEquals(Object input, Object value) {
        return !Objects.equals(input, value);
    }
}
