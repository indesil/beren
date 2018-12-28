package pl.jlabs.beren.compilator.utils;

import pl.jlabs.beren.compilator.parser.IterationType;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.regex.Pattern;

import static pl.jlabs.beren.compilator.parser.IterationType.NONE;

public class CodeUtils {
    private static final String VOID_TYPE = "java.lang.Void";
    private static final String INTERNAL_METHOD_PREFIX = "internal_";
    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");
    private static final String IMPLEMENTATION_SUFFIX = "ImplBeren_";
    private static final String AS_LIST = "asList";

    public static final String VALIDATION_RESULTS_PARAM = "validationResults";
    public static final String ITERATION_PARAM = "value";

    public static String createTemplatePlaceHolder(String key) {
        return "%\\{" + key + "\\}";
    }

    public static boolean iterationValidation(IterationType iterationType) {
        return !iterationType.equals(NONE);
    }

    public static String createPlaceHolderParams(String value) {
        return QUOTE_PATTERN.matcher(value).replaceAll("'");
    }

    public static String createMethodArgument(String value) {
        if(value.startsWith("(")) {
            return AS_LIST + value;
        }

        return value;
    }

    public static String createInternalMethodName(String methodName) {
        return INTERNAL_METHOD_PREFIX + methodName;
    }

    public static boolean isNotVoidType(TypeMirror type) {
        return !VOID_TYPE.equals(type.toString());
    }

    public static String createValidatorName(Element typeElement) {
        return typeElement.getSimpleName().toString() + IMPLEMENTATION_SUFFIX;
    }
}
