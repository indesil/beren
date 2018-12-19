package pl.jlabs.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface Validate {
    boolean nullable() default false;
    Field[] value();
}
