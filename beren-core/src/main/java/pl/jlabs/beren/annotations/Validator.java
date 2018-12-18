package pl.jlabs.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static pl.jlabs.beren.annotations.BreakingStrategy.STOP_ON_FIRST;
import static pl.jlabs.beren.annotations.MatchingStrategy.ONLY_DEFINED;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Validator {
    BreakingStrategy breakingStrategy() default STOP_ON_FIRST;
    MatchingStrategy matchingStrategy() default ONLY_DEFINED;
}
