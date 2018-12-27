package pl.jlabs.beren.compilator.utils;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.model.ValidationResults;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.tools.Diagnostic.Kind.ERROR;
import static pl.jlabs.beren.annotations.BreakingStrategy.SUMMARIZE_ALL;
import static pl.jlabs.beren.annotations.BreakingStrategy.THROW_ON_FIRST;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.*;

public class MethodsUtil {

    public static List<ExecutableElement> extractValidationParamFieldsGetters(VariableElement validationParam, ProcessingEnvironment processingEnv) {
        TypeElement paramElement = (TypeElement) processingEnv.getTypeUtils().asElement(validationParam.asType());
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(paramElement);
        return MethodsUtil.extractMethods(allMembers, processingEnv)
                .stream()
                .filter(CodeUtils::isGetterMethod)
                .collect(toList());
    }

    public static VariableElement getValidationMethodParam(ExecutableElement methodToImplement) {
        //validation methods must have one param!
        return methodToImplement.getParameters().get(0);
    }

    public static List<ExecutableElement> extractMethods(List<? extends Element> allTypeMembers, ProcessingEnvironment processingEnv) {
        List<ExecutableElement> allClassDefinedMethods = methodsIn(allTypeMembers);
        allClassDefinedMethods.removeAll(objectMethods(processingEnv));

        return allClassDefinedMethods;
    }

    private static List<ExecutableElement> objectMethods(ProcessingEnvironment processingEnv) {
        TypeElement objectElement = processingEnv.getElementUtils().getTypeElement(Object.class.getName());
        return methodsIn(objectElement.getEnclosedElements());
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

    public static boolean isValidMethodToImplement(ExecutableElement methodElement, BreakingStrategy breakingStrategy, ProcessingEnvironment processingEnv) {
        if(methodElement.getAnnotation(Validate.class) == null) {
            return false;
        }

        if(!methodElement.getModifiers().contains(Modifier.ABSTRACT)) {
            processingEnv.getMessager().printMessage(ERROR, format(NON_ABSTRACT,  methodElement.getSimpleName()));
            return false;
        }

        if(methodElement.getParameters().size() != 1) {
            processingEnv.getMessager().printMessage(ERROR, format(PARAMS_NUMBER,  methodElement.getSimpleName()));
            return false;
        }

        if(breakingStrategy.equals(THROW_ON_FIRST) && !(methodElement.getReturnType() instanceof NoType)) {
            processingEnv.getMessager().printMessage(ERROR, format(THROW_ON_FIRST_NOT_VOID,  methodElement.getSimpleName()));
            return false;
        }

        TypeMirror validationResultsType = processingEnv.getElementUtils().getTypeElement(ValidationResults.class.getName()).asType();
        if (breakingStrategy.equals(SUMMARIZE_ALL) && !methodElement.getReturnType().equals(validationResultsType)) {
            processingEnv.getMessager().printMessage(ERROR, format(SUMMARIZE_BAD_RETURN,  methodElement.getSimpleName()));
            return false;
        }
        return true;
    }
}
