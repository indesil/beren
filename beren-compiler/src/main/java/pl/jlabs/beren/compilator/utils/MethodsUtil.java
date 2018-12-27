package pl.jlabs.beren.compilator.utils;

import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.compilator.definitions.FieldDefinition;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;
import pl.jlabs.beren.compilator.methods.TypeMetadata;
import pl.jlabs.beren.model.ValidationResults;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.constructorsIn;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.tools.Diagnostic.Kind.ERROR;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static pl.jlabs.beren.annotations.BreakingStrategy.SUMMARIZE_ALL;
import static pl.jlabs.beren.annotations.BreakingStrategy.THROW_ON_FIRST;
import static pl.jlabs.beren.compilator.definitions.DefinitionBuilder.fromAnnotation;
import static pl.jlabs.beren.compilator.utils.CodeUtils.isNotVoidType;
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

    public static TypeMetadata extractTypeMetadata(TypeElement typeElement, ProcessingEnvironment processingEnv) {
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
        List<ExecutableElement> allClassDefinedMethods = extractMethods(allMembers, processingEnv);

        BreakingStrategy breakingStrategy = getBreakingStrategy(typeElement);
        TypeMetadata typeMetadata = new TypeMetadata(constructorsIn(allMembers), breakingStrategy);

        for(ExecutableElement methodElement : allClassDefinedMethods) {
            typeMetadata.addMethodReference(getMethodReference(methodElement), methodElement);
            if(isValidMethodToImplement(methodElement, breakingStrategy, processingEnv)) {
                ValidationDefinition validationDefinition = fromAnnotation(methodElement, processingEnv);
                if(isDefinitionValid(validationDefinition, processingEnv)) {
                    typeMetadata.addValidationDefinition(validationDefinition);
                }
            }
        }

        return typeMetadata;
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

    private static boolean isDefinitionValid(ValidationDefinition validationDefinition, ProcessingEnvironment processingEnv) {
        for (FieldDefinition fieldDefinition : validationDefinition.getFieldDefinitions()) {
            if(doesNotHaveOneSelector(fieldDefinition)) {
                processingEnv.getMessager().printMessage(ERROR, format(INVALID_SELECTORS_NUMBER, fieldDefinition));
                return false;
            }

            if(isNotEmpty(fieldDefinition.getPattern()) && isPattrnInvalid(fieldDefinition.getPattern(), processingEnv)) {
                return false;
            }


        }
        return true;
    }

    private static boolean doesNotHaveOneSelector(FieldDefinition fieldDefinition ) {
        String type = isNotVoidType(fieldDefinition.getType()) ? fieldDefinition.getType().toString() : null;
        String name = isNotEmpty(fieldDefinition.getName()) ? fieldDefinition.getName() : null;
        String[] names = ArrayUtils.isNotEmpty(fieldDefinition.getNames())
                && StringUtils.isNoneEmpty(fieldDefinition.getNames()) ? fieldDefinition.getNames() : null;
        String pattern = isNotEmpty(fieldDefinition.getPattern()) ? fieldDefinition.getPattern() : null;
        return Stream.of(name, names, pattern, type)
                .filter(Objects::nonNull)
                .collect(toList()).size() != 1;
    }

    private static boolean isPattrnInvalid(String pattern, ProcessingEnvironment processingEnv) {
        try {
            Pattern.compile(pattern);
            return false;
        } catch (PatternSyntaxException e) {
            processingEnv.getMessager().printMessage(ERROR, format(INVALID_PATTERN,  pattern, e.getMessage()));
            return true;
        }
    }
}
