package pl.indesil.beren.operations;

import java.util.Collection;
import java.util.List;

public class Operations {

    public static boolean neitherOf(String input, List<String> expectedStrings) {
        return !expectedStrings.contains(input);
    }

    public static boolean greaterThan(int number, int a) {
        return number > a;
    }

    public static boolean greaterThan(Integer number, int a) {
        return number != null && number > a;
    }

    public static boolean greaterThan(Double number, double a) {
        return number != null && number > a;
    }

    public static boolean between(Number number, int a, int b) {
        return number != null && number.intValue() >= a && number.intValue() <= b;
    }

    public static boolean between(Number number, double a, double b) {
        return number != null && number.doubleValue() >= a && number.doubleValue() <= b;
    }

    public static boolean notEmpty(CharSequence input) {
        return input != null && input.length() > 0;
    }

    public static boolean notEmpty(Collection col) {
        return col != null && !col.isEmpty();
    }
}
