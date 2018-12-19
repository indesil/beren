package pl.jlabs.beren.compilator.utils;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.compilator.methods.TypeMethods;
import pl.jlabs.beren.model.ValidationResults;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

import static java.lang.String.format;
import static javax.lang.model.util.ElementFilter.constructorsIn;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.tools.Diagnostic.Kind.ERROR;
import static pl.jlabs.beren.annotations.BreakingStrategy.SUMMARIZE_ALL;
import static pl.jlabs.beren.annotations.BreakingStrategy.THROW_ON_FIRST;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.*;

public class MethodsExtractor {

    public static TypeMethods extractMethods(TypeElement typeElement, ProcessingEnvironment processingEnv) {
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
        List<ExecutableElement> allClassDefinedMethods = methodsIn(allMembers);
        allClassDefinedMethods.removeAll(objectMethods(processingEnv));

        BreakingStrategy breakingStrategy = getBreakingStrategy(typeElement);
        TypeMethods typeMethods = new TypeMethods(constructorsIn(allMembers), breakingStrategy);

        for(ExecutableElement methodElement : allClassDefinedMethods) {
            typeMethods.addMethodReference(getMethodReference(methodElement), methodElement);
            if(isMethodToImplement(methodElement, breakingStrategy, processingEnv)) {
                typeMethods.addMethodToImplement(methodElement);
            }
        }

        return typeMethods;
    }

    private static List<ExecutableElement> objectMethods(ProcessingEnvironment processingEnv) {
        TypeElement objectElement = processingEnv.getElementUtils().getTypeElement(Object.class.getName());
        return methodsIn(objectElement.getEnclosedElements());
    }

    private static BreakingStrategy getBreakingStrategy(TypeElement typeElement) {
        Validator annotation = typeElement.getAnnotation(Validator.class);
        return annotation.breakingStrategy();
    }

    private static String getMethodReference(ExecutableElement methodElement) {
        Id idAnnotation = methodElement.getAnnotation(Id.class);
        if(idAnnotation != null) {
            return idAnnotation.value();
        }
        return methodElement.getSimpleName().toString();
    }

    private static boolean isMethodToImplement(ExecutableElement methodElement, BreakingStrategy breakingStrategy, ProcessingEnvironment processingEnv) {
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
