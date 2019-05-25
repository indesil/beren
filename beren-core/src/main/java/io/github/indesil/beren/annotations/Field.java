package io.github.indesil.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
public @interface Field {
    //! this should be choice! - add validation!
    String name() default "";
    String[] names() default {};
    Class type() default Void.class;
    String pattern() default "";
    String operation();
    String message() default "";
}
