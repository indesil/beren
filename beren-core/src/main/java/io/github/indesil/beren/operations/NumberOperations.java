package io.github.indesil.beren.operations;

import java.math.BigDecimal;
import java.math.BigInteger;

// To slightly increase performance create more function overloads for primitive types and Wrapper (since they can be nulls!)
public class NumberOperations {
    public static boolean min(BigInteger number, long value) {
        return number == null || number.compareTo(BigInteger.valueOf(value)) >= 0;
    }

    public static boolean min(BigDecimal number, long value) {
        return number == null || number.compareTo(BigDecimal.valueOf(value)) >= 0;
    }

    public static boolean min(Number number, long value) {
        return number == null || number.longValue() >= value;
    }

    public static boolean min(Number number, double value) {
        return number == null || number.doubleValue() >= value;
    }

    public static boolean max(BigInteger number, long value) {
        return number == null || number.compareTo(BigInteger.valueOf(value)) <= 0;
    }

    public static boolean max(BigDecimal number, long value) {
        return number == null || number.compareTo(BigDecimal.valueOf(value)) <= 0;
    }

    public static boolean max(Number number, long value) {
        return number == null || number.longValue() <= value;
    }

    public static boolean max(Number number, double value) {
        return number == null || number.doubleValue() <= value;
    }

    public static boolean negative(Number number) {
        return number == null || number.doubleValue() < 0;
    }

    public static boolean negative(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.signum() == -1;
    }

    public static boolean negative(BigInteger bigInteger) {
        return bigInteger == null || bigInteger.signum() == -1;
    }

    public static boolean negativeOrZero(Number number) {
        return number == null || number.longValue() <= 0;
    }

    public static boolean negativeOrZero(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.signum() <= 0;
    }

    public static boolean negativeOrZero(BigInteger bigInteger) {
        return bigInteger == null || bigInteger.signum() <= 0;
    }

    public static boolean positive(Number number) {
        return number == null || number.doubleValue() == 1;
    }

    public static boolean positive(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.signum() == 1;
    }

    public static boolean positive(BigInteger bigInteger) {
        return bigInteger == null || bigInteger.signum() == 1;
    }

    public static boolean positiveOrZero(Number number) {
        return number == null || number.doubleValue() >= 0;
    }

    public static boolean positiveOrZero(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.signum() >= 0;
    }

    public static boolean positiveOrZero(BigInteger bigInteger) {
        return bigInteger == null || bigInteger.signum() >= 0;
    }

    public static boolean between(BigDecimal number, long min, long max) {
        return number == null ||
                (number.compareTo(BigDecimal.valueOf(min)) >= 0 && number.compareTo(BigDecimal.valueOf(max)) <= 0);
    }

    public static boolean between(BigInteger number, long min, long max) {
        return number == null ||
                (number.compareTo(BigInteger.valueOf(min)) >= 0 && number.compareTo(BigInteger.valueOf(max)) <= 0);
    }

    public static boolean between(Number number, long min, long max) {
        return number == null || (number.intValue() >= min && number.intValue() <= max);
    }

    public static boolean between(Number number, double min, double max) {
        return number == null || (number.doubleValue() >= min && number.doubleValue() <= max);
    }
}