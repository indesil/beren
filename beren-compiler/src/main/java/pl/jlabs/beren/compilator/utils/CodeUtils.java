package pl.jlabs.beren.compilator.utils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class CodeUtils {
    private static final String INTERNAL_METHOD_PREFIX = "internal_";
    public static final String VALIDATION_RESULTS_PARAM = "validationResults";

    public static final String createInternalMethodName(String methodName) {
        return INTERNAL_METHOD_PREFIX + methodName;
    }

    public static VariableElement getValidationMethodParam(ExecutableElement methodToImplement) {
        //validation methods must have one param!
        return methodToImplement.getParameters().get(0);
    }
}
