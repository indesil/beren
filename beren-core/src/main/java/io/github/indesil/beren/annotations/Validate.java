package io.github.indesil.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static io.github.indesil.beren.annotations.SourceType.METHOD_DEFINITION;

@Target(ElementType.METHOD)
public @interface Validate {
    Source source() default @Source(type = METHOD_DEFINITION);
    boolean nullable() default false;
    //should be javax.validation.constraints.NotNull.message!
    String nullableMessage() default "%{input} must not be null!";
    Field[] value();
}
