package pl.jlabs.beren.operations;

import java.util.function.Predicate;

public class Between implements Predicate<Integer> {
    private int a;
    private int b;

    public Between(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean test(Integer integer) {
        return integer > a && integer < b;
    }
}
