package pl.jlabs.beren.compilator.utils;

import pl.jlabs.beren.compilator.parser.IterationType;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.util.regex.Pattern;

import static pl.jlabs.beren.compilator.parser.IterationType.NONE;

public class CodeUtils {
    private static final String VOID_TYPE = "java.lang.Void";
    private static final String GET_PREFIX = "get";
    private static final String IS_PREFIX = "is";

    private static final String INTERNAL_METHOD_PREFIX = "internal_";
    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");
    public static final String VALIDATION_RESULTS_PARAM = "validationResults";
    public static final String ITERATION_PARAM = "value";

    public static final String createTemplatePlaceHolder(String key) {
        return "%\\{" + key + "\\}";
    }

    public static boolean iterationValidation(IterationType iterationType) {
        return !iterationType.equals(NONE);
    }

    public static final String createPlaceHolderParams(String value) {
        return QUOTE_PATTERN.matcher(value).replaceAll("'");
    }

    public static final String createMethodArgument(String value) {
        if(value.startsWith("(")) {
            return "asList" + value;
        }

        return value;
    }

    public static final String createInternalMethodName(String methodName) {
        return INTERNAL_METHOD_PREFIX + methodName;
    }

    public static String extractFieldName(String getterMethod) {
        String getter = getGetterPlainName(getterMethod);
        return getter.substring(0, 1).toLowerCase() + getter.substring(1);
    }

    public static boolean isGetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith(GET_PREFIX) || methodName.startsWith(IS_PREFIX);
    }

    public static String normalizeGetterName(String getterName) {
        return getGetterPlainName(getterName).toLowerCase();
    }

    private static String getGetterPlainName(String getterName) {
        if(getterName.startsWith(GET_PREFIX)) {
            return getterName.substring(3);
        }

        return getterName.substring(2);
    }

    public static boolean isNotVoidType(TypeMirror type) {
        return !VOID_TYPE.equals(type.toString());
    }
}
