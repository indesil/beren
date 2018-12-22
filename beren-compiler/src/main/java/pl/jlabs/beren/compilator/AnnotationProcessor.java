package pl.jlabs.beren.compilator;

import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.compilator.utils.MethodsExtractor;
import pl.jlabs.beren.compilator.methods.TypeMetadata;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
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
import static pl.jlabs.beren.compilator.configuration.ConfigurationLoader.loadConfigurations;

@SupportedAnnotationTypes({"pl.jlabs.beren.annotations.Validator"})
public class AnnotationProcessor extends AbstractProcessor {
    private ClassCodeGenerator classCodeGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        classCodeGenerator = new ClassCodeGenerator(processingEnv, loadConfigurations());
        processingEnv.getMessager().printMessage(MANDATORY_WARNING, "Annotation processor init");
    }

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
        for (Element typeElement : validatorElements) {
            try {
                if(isEitherInterfaceOrClass(typeElement.getKind())) {
                    TypeMetadata typeMetadata = MethodsExtractor.extractTypeMetadata((TypeElement) typeElement, processingEnv);
                    processingEnv.getMessager().printMessage(MANDATORY_WARNING, format("generating validator for %s", typeElement.toString()));
                    classCodeGenerator.generateJavaClass(typeElement, typeMetadata);
                } else {
                    processingEnv.getMessager().printMessage(ERROR, format("Type %s is not class nor interface!",  typeElement.toString()));
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
