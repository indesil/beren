package pl.jlabs.beren.compilator;

import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.compilator.methods.ClassMethods;
import pl.jlabs.beren.compilator.methods.MethodsExtractor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

import static java.lang.String.format;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.MANDATORY_WARNING;

@SupportedAnnotationTypes({"pl.jlabs.beren.annotations.Validator"})
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(MANDATORY_WARNING, "Beren generating validator code");
        if (!annotations.isEmpty()) {
            generateValidatorCode(annotations, roundEnv);
        }
        processingEnv.getMessager().printMessage(MANDATORY_WARNING, "Generation finished");

        return true;
    }

    private void generateValidatorCode(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> validatorElements = roundEnv.getElementsAnnotatedWith(Validator.class);
        processingEnv.getMessager().printMessage(MANDATORY_WARNING, "number of validator classes " + validatorElements.size());
        for (Element element : validatorElements) {
            try {
                if(isEitherInterfaceOrClass(element.getKind())) {
                    ClassMethods classMethods = MethodsExtractor.extractMethods(element, processingEnv);
                    processingEnv.getMessager().printMessage(MANDATORY_WARNING, format("generating validator for %s", element.toString()));
                    ClassCodeGenerator.generateJavaClass(element, classMethods, processingEnv);
                } else {
                    processingEnv.getMessager().printMessage(ERROR, format("Type %s is not class nor interface!",  element.toString()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEitherInterfaceOrClass(ElementKind elementKind) {
        return ElementKind.CLASS.equals(elementKind) || ElementKind.INTERFACE.equals(elementKind);
    }
}
