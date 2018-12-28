package pl.jlabs.beren.compilator.parser;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.SourceType;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.parser.source.ParserContext;
import pl.jlabs.beren.compilator.parser.source.SourceParserFactory;
import pl.jlabs.beren.compilator.utils.ProcessingFacade;
import pl.jlabs.beren.model.ValidationResults;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.jlabs.beren.annotations.BreakingStrategy.SUMMARIZE_ALL;
import static pl.jlabs.beren.annotations.BreakingStrategy.THROW_ON_FIRST;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.*;
import static pl.jlabs.beren.compilator.utils.MethodsUtil.createMethodReferences;
import static pl.jlabs.beren.compilator.utils.MethodsUtil.getBreakingStrategy;

public class DefinitionParser {
    private ProcessingFacade processingFacade;
    private SourceParserFactory sourceParserFactory;
    private TypeMirror validationResultsType;

    public DefinitionParser(ProcessingFacade processingFacade) {
        this.processingFacade = processingFacade;
        this.sourceParserFactory = new SourceParserFactory(processingFacade);
        validationResultsType = processingFacade.getElementType(ValidationResults.class);
    }

    public ValidatorDefinition parse(TypeElement typeElement) {
        BreakingStrategy breakingStrategy = getBreakingStrategy(typeElement);
        return new ValidatorDefinition()
                .withBreakingStrategy(breakingStrategy)
                .withConstructors(processingFacade.extractTypeConstructors(typeElement))
                .withValidationDefinitions(createValidationDefinitions(typeElement, breakingStrategy));
    }

    private List<ValidationDefinition> createValidationDefinitions(TypeElement typeElement, BreakingStrategy breakingStrategy) {
        List<ExecutableElement> allClassDefinedMethods = processingFacade.extractTypeMethods(typeElement);
        Map<String, ExecutableElement> methodReferences = createMethodReferences(allClassDefinedMethods);
        return allClassDefinedMethods.stream()
                .filter(method -> isValidMethodToImplement(method, breakingStrategy))
                .map(method -> createDefinitionFromProperSource(method, methodReferences))
                .collect(Collectors.toList());
    }

    private boolean isValidMethodToImplement(ExecutableElement methodElement, BreakingStrategy breakingStrategy) {
        if(methodElement.getAnnotation(Validate.class) == null) {
            return false;
        }

        if(!methodElement.getModifiers().contains(Modifier.ABSTRACT)) {
            processingFacade.error(NON_ABSTRACT, methodElement.getSimpleName());
            return false;
        }

        //validation methods must have one param!
        if(methodElement.getParameters().size() != 1) {
            processingFacade.error(PARAMS_NUMBER,  methodElement.getSimpleName());
            return false;
        }

        if(breakingStrategy.equals(THROW_ON_FIRST) && !(methodElement.getReturnType() instanceof NoType)) {
            processingFacade.error(THROW_ON_FIRST_NOT_VOID,  methodElement.getSimpleName());
            return false;
        }

        if (breakingStrategy.equals(SUMMARIZE_ALL) && !methodElement.getReturnType().equals(validationResultsType)) {
            processingFacade.error(SUMMARIZE_BAD_RETURN,  methodElement.getSimpleName());
            return false;
        }
        return true;
    }

    private ValidationDefinition createDefinitionFromProperSource(ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        Validate validateAnnotation = methodToImplement.getAnnotation(Validate.class);
        SourceType sourceType = validateAnnotation.source().type();
        ParserContext parserContext = createParserContext(methodToImplement, methodReferences);
        return sourceParserFactory.obtainParserForSource(sourceType).parse(parserContext);
    }

    private ParserContext createParserContext(ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        VariableElement validationMethodParam = methodToImplement.getParameters().get(0);
        return new ParserContext()
                .withMethodReferences(methodReferences)
                .withMethodToImplement(methodToImplement)
                .withValidationMethodParam(validationMethodParam)
                .withValidationParamFieldsGetters(processingFacade.extractGetterMethods(validationMethodParam));
    }
}
