package pl.jlabs.beren.compilator.parser;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.SourceType;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.parser.source.ParserContext;
import pl.jlabs.beren.compilator.parser.source.SourceParserFactory;
import pl.jlabs.beren.compilator.utils.MethodsUtil;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.lang.model.util.ElementFilter.constructorsIn;
import static pl.jlabs.beren.compilator.utils.MethodsUtil.*;

public class DefinitionParser {
    private ProcessingEnvironment processingEnv;
    private SourceParserFactory sourceParserFactory;

    public DefinitionParser(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.sourceParserFactory = new SourceParserFactory(processingEnv, berenConfig);
    }

    public ValidatorDefinition parse(TypeElement typeElement) {
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
        List<ExecutableElement> allClassDefinedMethods = extractMethods(allMembers, processingEnv);

        BreakingStrategy breakingStrategy = getBreakingStrategy(typeElement);
        return new ValidatorDefinition()
                .withBreakingStrategy(breakingStrategy)
                .withConstructors(constructorsIn(allMembers))
                .withValidationDefinitions(createValidationDefinitions(allClassDefinedMethods, breakingStrategy));
    }

    private List<ValidationDefinition> createValidationDefinitions(List<ExecutableElement> allClassDefinedMethods, BreakingStrategy breakingStrategy) {
        Map<String, ExecutableElement> methodReferences = MethodsUtil.createMethodReferences(allClassDefinedMethods);
        return allClassDefinedMethods.stream()
                .filter(method -> isValidMethodToImplement(method, breakingStrategy, processingEnv))
                .map(method -> createDefinitionFromProperSource(method, methodReferences))
                .collect(Collectors.toList());
    }

    private ValidationDefinition createDefinitionFromProperSource(ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        Validate validateAnnotation = methodToImplement.getAnnotation(Validate.class);
        SourceType sourceType = validateAnnotation.source().type();
        return sourceParserFactory.obtainParserForSource(sourceType)
                .parse(createParserContext(methodToImplement, methodReferences));
    }

    private ParserContext createParserContext(ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        VariableElement validationMethodParam = getValidationMethodParam(methodToImplement);
        return new ParserContext()
                .withMethodReferences(methodReferences)
                .withMethodToImplement(methodToImplement)
                .withValidationMethodParam(validationMethodParam)
                .withValidationParamFieldsGetters(extractValidationParamFieldsGetters(validationMethodParam, processingEnv));
    }
}
