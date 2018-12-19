package pl.jlabs.beren.compilator.methods;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.model.ValidationResults;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;

import static java.lang.String.format;
import static javax.lang.model.util.ElementFilter.constructorsIn;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.tools.Diagnostic.Kind.ERROR;

public class MethodsExtractor {

    public static TypeMethods extractMethods(TypeElement typeElement, ProcessingEnvironment processingEnv) {
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
        List<ExecutableElement> allClassDefinedMethods = methodsIn(allMembers);
        allClassDefinedMethods.removeAll(objectMethods(processingEnv));

        TypeMethods typeMethods = new TypeMethods(constructorsIn(allMembers), getBreakingStrategy(typeElement));

        for(ExecutableElement methodElement : allClassDefinedMethods) {
            typeMethods.addMethodReference(getMethodReference(methodElement), methodElement);
            if(isMethodToImplement(methodElement, processingEnv)) {
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

    private static boolean isMethodToImplement(ExecutableElement methodElement, ProcessingEnvironment processingEnv) {
        if(methodElement.getAnnotation(Validate.class) == null) {
            return false;
        }

        if(!methodElement.getModifiers().contains(Modifier.ABSTRACT)) {
            processingEnv.getMessager().printMessage(ERROR, format("Validator method %s must be abstract to override!",  methodElement.getSimpleName()));
            return false;
        }

        if(methodElement.getParameters().size() != 1) {
            processingEnv.getMessager().printMessage(ERROR, format("Validator method %s must have 1 (and only 1) input parameter!",  methodElement.getSimpleName()));
            return false;
        }

        TypeElement validationResultsElement = processingEnv.getElementUtils().getTypeElement(ValidationResults.class.getName());
        if (!methodElement.getReturnType().equals(validationResultsElement.asType())) {
            processingEnv.getMessager().printMessage(ERROR, format("Validator method %s must return ValidationResults!",  methodElement.getSimpleName()));
            return false;
        }
        return true;
    }
}
