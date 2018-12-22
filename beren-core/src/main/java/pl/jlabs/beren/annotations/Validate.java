package pl.jlabs.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static pl.jlabs.beren.annotations.SourceType.METHOD_DEFINITION;

@Target(ElementType.METHOD)
public @interface Validate {
    Source source() default @Source(type = METHOD_DEFINITION);
    boolean nullable() default false;
    String nullableMessage() default "%{paramName} must not be null!";
    Field[] value();
}
