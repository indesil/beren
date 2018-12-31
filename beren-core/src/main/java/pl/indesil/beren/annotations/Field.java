package pl.indesil.beren.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
public @interface Field {
    //! to powinien byc choice! dodac waldiacje!
    String name() default "";
    String[] names() default {};
    Class type() default Void.class;
    String pattern() default "";
    String operation();
    String message() default "";
}
