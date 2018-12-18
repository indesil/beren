package pl.jlabs.beren.operations;

public interface Operation<T> {
    boolean isValid(T input);
}
