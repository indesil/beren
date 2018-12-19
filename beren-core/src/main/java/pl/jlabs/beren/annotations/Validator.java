package pl.jlabs.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static pl.jlabs.beren.annotations.BreakingStrategy.THROW_ON_FIRST;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Validator {
    BreakingStrategy breakingStrategy() default THROW_ON_FIRST;
}
