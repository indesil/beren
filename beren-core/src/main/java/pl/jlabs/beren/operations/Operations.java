package pl.jlabs.beren.operations;

import java.util.List;

public class Operations {

    public static final boolean neitherOf(String input, List<String> expectedStrings) {
        return !expectedStrings.contains(input);
    }

    public static final boolean neitherOf(String input, String[] expectedStrings) {
        for (String expectedString : expectedStrings) {
            if(expectedString.equals(input)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean biggerThan(Number number, int a) {
        return number != null && number.intValue() > a;
    }

    public static final boolean biggerThan(Number number, double a) {
        return number != null && number.doubleValue() > a;
    }

    public static final boolean between(Number number, int a, int b) {
        return number != null && number.intValue() >= a && number.intValue() <= b;
    }

    public static final boolean between(Number number, double a, double b) {
        return number != null && number.doubleValue() >= a && number.doubleValue() <= b;
    }
}
