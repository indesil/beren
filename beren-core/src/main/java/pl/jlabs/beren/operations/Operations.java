package pl.jlabs.beren.operations;

import java.util.Objects;
import java.util.function.Predicate;

public class Operations {
    //  notNull: pl.jlabs.beren.Operations.notNull
    //  between(a,b): pl.jlabs.beren.Operations.between(a,b)
    //  notEmpty: pl.jlabs.beren.Operations.notEmpty
    //  collectionNotEmpty: pl.jlabs.beren.Operations.collectionNotEmpty
    //  atLeastOneTrue: pl.jlabs.beren.Operations.atLeastOneTrue
    //  custom: pl.your.package.CustomValidator.someValidator

    public static final Predicate<Object> notNull() {
        return Objects::nonNull;
    }

    public static final Predicate<Number> between(int a, int b) {
        return number -> number.intValue() > a && number.intValue() < b;
    }
}
