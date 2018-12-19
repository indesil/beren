package pl.jlabs.beren.operations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Operations {
    //  notNull: pl.jlabs.beren.Operations.notNull
    //  between(a,b): pl.jlabs.beren.Operations.between(a,b)
    //  notEmpty: pl.jlabs.beren.Operations.notEmpty
    //  collectionNotEmpty: pl.jlabs.beren.Operations.collectionNotEmpty
    //  atLeastOneTrue: pl.jlabs.beren.Operations.atLeastOneTrue
    //  custom: pl.your.package.CustomValidator.someValidator

    //  public static final boolean notNull(Object input) {
    //        return input != null;
    //    }
    public static final Predicate<Object> notNull() {
        return Objects::nonNull;
    }
    //public static final boolean neitherOf(String value, List<String> expectedStrings) {
    //  return expectedStrings.contains(value);
    //}

    public static final Predicate<String> neitherOf(List<String> expectedStrings) {
        return input -> !expectedStrings.contains(input);
    }

    public static final Predicate<Number> biggerThan(int a) {
        return number -> number != null && number.intValue() > a;
    }

    public static final Predicate<Number> between(int a, int b) {
        return number -> number.intValue() > a && number.intValue() < b;
    }
}
