package pl.jlabs.beren.compilator.utils;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validator;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MethodsUtil {

    private static final String GET_PREFIX = "get";
    private static final String IS_PREFIX = "is";

    public static String extractFieldName(String getterMethod) {
        String getter = getGetterPlainName(getterMethod);
        return getter.substring(0, 1).toLowerCase() + getter.substring(1);
    }

    public static String normalizeGetterName(String getterName) {
        return getGetterPlainName(getterName).toLowerCase();
    }

    public static boolean isGetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith(GET_PREFIX) || methodName.startsWith(IS_PREFIX);
    }

    private static String getGetterPlainName(String getterName) {
        if(getterName.startsWith(GET_PREFIX)) {
            return getterName.substring(3);
        }

        return getterName.substring(2);
    }

    public static BreakingStrategy getBreakingStrategy(TypeElement typeElement) {
        Validator annotation = typeElement.getAnnotation(Validator.class);
        return annotation.breakingStrategy();
    }

    public static Map<String, ExecutableElement> createMethodReferences(List<ExecutableElement> methods) {
        return methods.stream().collect(Collectors.toMap(MethodsUtil::getMethodReference, Function.identity()));
    }

    private static String getMethodReference(ExecutableElement methodElement) {
        Id idAnnotation = methodElement.getAnnotation(Id.class);
        if(idAnnotation != null) {
            return idAnnotation.value();
        }
        return methodElement.getSimpleName().toString();
    }
}
