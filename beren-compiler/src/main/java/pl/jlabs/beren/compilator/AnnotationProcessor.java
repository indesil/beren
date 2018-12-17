package pl.jlabs.beren.compilator;

import pl.jlabs.beren.annotations.Validator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"pl.jlabs.beren.annotations.Validator"})
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Beren generating validator code");
        if (!annotations.isEmpty()) {
            generateValidatorCode(annotations, roundEnv);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Generation finished");

        return true;
    }

    private void generateValidatorCode(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> validatorElements = roundEnv.getElementsAnnotatedWith(Validator.class);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "number of validator classes " + validatorElements.size());
        for (Element element : validatorElements) {
            Class<? extends Element> elementClass = element.getClass();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Generating validator for class " +element.toString());
            try {
                CodeGenerator.generateCode(element, processingEnv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
