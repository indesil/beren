package io.github.indesil.beren.compilator;

import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.compilator.configuration.BerenConfig;
import io.github.indesil.beren.compilator.configuration.ConfigurationLoader;
import io.github.indesil.beren.compilator.parser.DefinitionParser;
import io.github.indesil.beren.compilator.parser.ValidatorDefinition;
import io.github.indesil.beren.compilator.utils.ErrorMessages;
import io.github.indesil.beren.compilator.utils.ProcessingFacade;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Optional;
import java.util.Set;

@SupportedAnnotationTypes("io.github.indesil.beren.annotations.Validator")
public class AnnotationProcessor extends AbstractProcessor {
    private DefinitionParser definitionParser;
    private ProcessingFacade processingFacade;
    private ClassCodeGenerator classCodeGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        loadConfig(processingEnv);
        processingFacade = new ProcessingFacade(processingEnv, loadConfig(processingEnv));
        definitionParser = new DefinitionParser(processingFacade);
        classCodeGenerator = new ClassCodeGenerator(processingFacade);
        processingFacade.warning("Beren annotation processor init");
    }

    private BerenConfig loadConfig(ProcessingEnvironment processingEnv) {
        try {
            return ConfigurationLoader.loadConfigurations();
        } catch (Exception e) {
            String message = String.format(ErrorMessages.BEREN_CONFIG_FAIL, e.getMessage());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
        }

        return null;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!processingFacade.successFullConfigInit()) {
            return false;
        }

        processingFacade.warning("Beren generating validator code");
        Set<? extends Element> validatorElements = roundEnv.getElementsAnnotatedWith(Validator.class);
        if (!validatorElements.isEmpty()) {
            generateValidatorCode(validatorElements, roundEnv);
        }
        processingFacade.warning("Generation finished");
        return true;
    }

    private void generateValidatorCode(Set<? extends Element> validatorElements, RoundEnvironment roundEnv) {
        processingFacade.warning("number of validator classes %s", validatorElements.size());
        Optional<? extends Element> invalidElement = findInvalidAnnotationUsage(validatorElements);
        if (invalidElement.isPresent()) {
            processingFacade.error("@Validator can only be used on interface or class! Invalid class %s", invalidElement.get().getSimpleName());
            return;
        }

        generateValidatorClassesForTypes(validatorElements);
    }

    private Optional<? extends Element> findInvalidAnnotationUsage(Set<? extends Element> validatorElements) {
        return validatorElements.stream().filter(this::isNeitherInterfaceNorClass).findAny();
    }

    private boolean isNeitherInterfaceNorClass(Element element) {
        ElementKind elementKind = element.getKind();
        return !ElementKind.CLASS.equals(elementKind) && !ElementKind.INTERFACE.equals(elementKind);
    }

    private void generateValidatorClassesForTypes(Set<? extends Element> validatorElements) {
        for (Element typeElement : validatorElements) {
            processingFacade.warning("generating validator for class %s", typeElement.toString());
            ValidatorDefinition validatorDefinition = definitionParser.parse((TypeElement) typeElement);
            try {
                classCodeGenerator.generateJavaClass(typeElement, validatorDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
