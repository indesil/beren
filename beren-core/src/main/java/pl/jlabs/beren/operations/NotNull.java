package pl.jlabs.beren.operations;

import java.util.function.Predicate;

public class NotNull<Object> implements Predicate<Object> {

    @Override
    public boolean test(Object object) {
        return object != null;
    }
}
