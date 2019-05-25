package io.github.indesil.beren.operations;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    public static boolean min(Float number, long value) {
        return number == null || number >= value;
    }

    public static boolean min(Double number, long value) {
        return number == null || number >= value;
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

    public static boolean max(Float number, long value) {
        return number == null || number <= value;
    }

    public static boolean max(Double number, long value) {
        return number == null || number <= value;
    }

    public static boolean decimalMin(BigInteger number, String decimal, boolean inclusive) {
        try {
            return decimalMin(number, new BigDecimal(decimal), inclusive);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean decimalMin(BigInteger number, BigDecimal decimal, boolean inclusive) {
        BigDecimal bigDecimalNumber = number != null ? new BigDecimal(number) : null;
        return decimalMin(bigDecimalNumber, decimal, inclusive);
    }

    public static boolean decimalMin(BigDecimal number, String decimal, boolean inclusive) {
        try {
            return decimalMin(number, new BigDecimal(decimal), inclusive);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean decimalMin(Number number, String decimal, boolean inclusive) {
        BigDecimal bigDecimalNumber = number != null ? new BigDecimal(number.toString()) : null;
        return decimalMin(bigDecimalNumber, decimal, inclusive);
    }

    public static boolean decimalMin(CharSequence number, String decimal, boolean inclusive) {
        try {
            BigDecimal bigDecimalNumber = number != null ? new BigDecimal(number.toString()) : null;
            return decimalMin(bigDecimalNumber, new BigDecimal(decimal), inclusive);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean decimalMin(BigDecimal valueToValidate, BigDecimal expectedMax, boolean inclusive) {
        if (valueToValidate == null) {
            return true;
        }
        int compareValue = valueToValidate.compareTo(expectedMax);
        return inclusive ? compareValue >= 0 : compareValue > 0;
    }

    public static boolean decimalMax(BigInteger number, String decimal, boolean inclusive) {
        try {
            return decimalMax(number, new BigDecimal(decimal), inclusive);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean decimalMax(BigInteger number, BigDecimal decimal, boolean inclusive) {
        BigDecimal bigDecimalNumber = number != null ? new BigDecimal(number) : null;
        return decimalMax(bigDecimalNumber, decimal, inclusive);
    }

    public static boolean decimalMax(BigDecimal number, String decimal, boolean inclusive) {
        try {
            return decimalMax(number, new BigDecimal(decimal), inclusive);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean decimalMax(Number number, String decimal, boolean inclusive) {
        BigDecimal bigDecimalNumber = number != null ? new BigDecimal(number.toString()) : null;
        return decimalMax(bigDecimalNumber, decimal, inclusive);
    }

    public static boolean decimalMax(CharSequence number, String decimal, boolean inclusive) {
        try {
            BigDecimal bigDecimalNumber = number != null ? new BigDecimal(number.toString()) : null;
            return decimalMax(bigDecimalNumber, new BigDecimal(decimal), inclusive);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean decimalMax(BigDecimal valueToValidate, BigDecimal expectedMax, boolean inclusive) {
        if (valueToValidate == null) {
            return true;
        }
        int compareValue = valueToValidate.compareTo(expectedMax);
        return inclusive ? compareValue <= 0 : compareValue < 0;
    }

    public static boolean negative(Number number) {
        return number == null || number.longValue() < 0;
    }

    public static boolean negative(Float number) {
        return number == null || number < 0.0f;
    }

    public static boolean negative(Double number) {
        return number == null || number < 0.0;
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

    public static boolean negativeOrZero(Float number) {
        return number == null || number <= 0.0f;
    }

    public static boolean negativeOrZero(Double number) {
        return number == null || number <= 0.0;
    }

    public static boolean negativeOrZero(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.signum() <= 0;
    }

    public static boolean negativeOrZero(BigInteger bigInteger) {
        return bigInteger == null || bigInteger.signum() <= 0;
    }

    public static boolean positive(Number number) {
        return number == null || number.longValue() > 0;
    }

    public static boolean positive(Float number) {
        return number == null || number > 0.0f;
    }

    public static boolean positive(Double number) {
        return number == null || number > 0.0;
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

    public static boolean positiveOrZero(Float number) {
        return number == null || number >= 0.0f;
    }

    public static boolean positiveOrZero(Double number) {
        return number == null || number >= 0.0;
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

    public static boolean between(Float number, int min, int max) {
        return number == null || (number >= min && number <= max);
    }

    public static boolean between(Double number, int min, int max) {
        return number == null || (number >= min && number <= max);
    }

    public static boolean between(Number number, double min, double max) {
        return number == null || (number.doubleValue() >= min && number.doubleValue() <= max);
    }

    public static boolean digits(CharSequence valueToValidate, int maxIntegerLength, int maxFractionLength) {
        return valueToValidate == null || digits(valueToValidate.toString(), maxIntegerLength, maxFractionLength);
    }

    public static boolean digits(Number valueToValidate, int maxIntegerLength, int maxFractionLength) {
        return valueToValidate == null || digits(valueToValidate.toString(), maxIntegerLength, maxFractionLength);
    }

    public static boolean digits(String valueToValidate, int maxIntegerLength, int maxFractionLength) {
        try {
            return valueToValidate == null || digits(new BigDecimal(valueToValidate), maxIntegerLength, maxFractionLength);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean digits(BigDecimal valueToValidate, int maxIntegerLength, int maxFractionLength) {
        if (valueToValidate == null) {
            return true;
        }

        int integerPartLength = valueToValidate.precision() - valueToValidate.scale();
        int fractionPartLength = valueToValidate.scale() < 0 ? 0 : valueToValidate.scale();

        return (maxIntegerLength >= integerPartLength && maxFractionLength >= fractionPartLength);
    }
}