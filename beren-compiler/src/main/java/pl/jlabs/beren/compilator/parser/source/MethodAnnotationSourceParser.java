package pl.jlabs.beren.compilator.parser.source;

import org.apache.commons.lang3.StringUtils;
import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.configuration.OperationConfig;
import pl.jlabs.beren.compilator.definitions.PlaceHolders;
import pl.jlabs.beren.compilator.parser.FieldValidationDefinition;
import pl.jlabs.beren.compilator.parser.RawFieldDefinition;
import pl.jlabs.beren.compilator.parser.ValidationDefinition;
import pl.jlabs.beren.compilator.utils.CodeUtils;
import pl.jlabs.beren.compilator.utils.OperationUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static javax.tools.Diagnostic.Kind.ERROR;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static pl.jlabs.beren.compilator.definitions.PlaceHolders.THIS_;
import static pl.jlabs.beren.compilator.parser.FieldValidationDefinition.OperationType.*;
import static pl.jlabs.beren.compilator.utils.CodeUtils.extractFieldName;
import static pl.jlabs.beren.compilator.utils.CodeUtils.isNotVoidType;
import static pl.jlabs.beren.compilator.utils.DefinitionUtils.createRawFieldDefinition;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.*;

public class MethodAnnotationSourceParser implements SourceParser {
    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;

    public MethodAnnotationSourceParser(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
    }

    @Override
    public ValidationDefinition parse(ParserContext parserContext) {
        Validate validateAnnotation = parserContext.getMethodToImplement().getAnnotation(Validate.class);
        return new ValidationDefinition()
                .withNullable(validateAnnotation.nullable())
                .withNullableMessageTemplate(validateAnnotation.nullableMessage())
                .withFieldsToValidate(createFieldsDefinitions(validateAnnotation.value(), parserContext))
                .withValidatedParamName(parserContext.getValidationMethodParam().getSimpleName().toString());
    }

    private List<FieldValidationDefinition> createFieldsDefinitions(Field[] fields, ParserContext parserContext) {
        String methodName = parserContext.getMethodToImplementName();
        if(fields == null || fields.length == 0) {
            processingEnv.getMessager().printMessage(ERROR, format(VALIDATION_FIELDS_EMPTY, methodName));
            return emptyList();
        }

        List<FieldValidationDefinition> fieldValidationDefinitions = new ArrayList<>();
        for (Field field : fields) {
            RawFieldDefinition rawFieldDefinition = createRawFieldDefinition(field, methodName, processingEnv);
            if(rawFieldDefinition != null) {
                fieldValidationDefinitions.addAll(createDefinitionsForField(rawFieldDefinition, parserContext));
            } else {
                break;
            }
        }

        return fieldValidationDefinitions;
    }

    private List<FieldValidationDefinition> createDefinitionsForField(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        if(isNotEmpty(rawFieldDefinition.getName())) {
            return createDefinitionForSingleName(rawFieldDefinition, parserContext);
        }
        if(rawFieldDefinition.getNames() != null && rawFieldDefinition.getNames().length > 0) {
            return createDefinitionForMultipleNames(rawFieldDefinition, parserContext);
        }
        if(rawFieldDefinition.getPattern() != null) {
            return createDefinitionFromPattern(rawFieldDefinition, parserContext);
        }
        if(isNotVoidType(rawFieldDefinition.getType())) {
            return createDefinitionFromType(rawFieldDefinition, parserContext);
        }
        return emptyList();
    }

    private List<FieldValidationDefinition> createDefinitionForSingleName(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) { ;
        String fieldGetter = findFieldGetter(rawFieldDefinition.getName(), parserContext);
        if(isNotEmpty(fieldGetter)) {
            return singletonList(createFieldDefinition(rawFieldDefinition, parserContext, fieldGetter));
        }
        return emptyList();
    }

    private List<FieldValidationDefinition> createDefinitionForMultipleNames(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        return Stream.of(rawFieldDefinition.getNames())
                .map(fieldName -> findFieldGetter(fieldName, parserContext))
                .filter(StringUtils::isNotEmpty)
                .map(fieldGetter -> createFieldDefinition(rawFieldDefinition, parserContext, fieldGetter))
                .collect(toList());
    }

    private List<FieldValidationDefinition> createDefinitionFromPattern(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        Pattern pattern = rawFieldDefinition.getPattern();
        return parserContext.getValidationParamFieldsGetters().stream()
                .map(getter -> getter.getSimpleName().toString())
                .filter(getterName -> pattern.matcher(extractFieldName(getterName)).matches())
                .map(getterName -> createFieldDefinition(rawFieldDefinition, parserContext, getterName))
                .collect(toList());
    }

    private List<FieldValidationDefinition> createDefinitionFromType(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        return parserContext.getValidationParamFieldsGetters().stream()
                .filter(getter -> getter.getReturnType().equals(rawFieldDefinition.getType()))
                .map(getter -> getter.getSimpleName().toString())
                .map(getterName -> createFieldDefinition(rawFieldDefinition, parserContext, getterName))
                .collect(toList());
    }

    private FieldValidationDefinition createFieldDefinition(RawFieldDefinition rawFieldDefinition, ParserContext parserContext, String fieldGetter) {
        String operationRef = OperationUtils.strapFromParams(rawFieldDefinition.getOperation());
        ExecutableElement executableElement = parserContext.getMethodReferences().get(operationRef);

        Map<String, String> params = mapWithThisParam(fieldGetter, parserContext.getValidationMethodParamName());
        if(executableElement != null) {
            return fromClassMethodRef(executableElement, rawFieldDefinition, params);
        }

        return fromStaticMethod(operationRef, rawFieldDefinition, params);
    }

    private String findFieldGetter(String fieldName, ParserContext parserContext) {
        String lowercaseFieldName = fieldName.toLowerCase();
        Optional<ExecutableElement> fieldGetter = parserContext.getValidationParamFieldsGetters()
                .stream()
                .filter(getter -> CodeUtils.normalizeGetterName(getter.getSimpleName().toString()).equals(lowercaseFieldName))
                .findFirst();
        if(fieldGetter.isPresent()) {
            return fieldGetter.get().getSimpleName().toString();
        }
        processingEnv.getMessager().printMessage(ERROR, format(FIELD_GETTER_NOT_FOUND, parserContext.getValidationMethodParamName(), fieldName));
        return "";
    }

    private FieldValidationDefinition fromClassMethodRef(ExecutableElement executableElement, RawFieldDefinition rawFieldDefinition, Map<String, String> params) {
        FieldValidationDefinition.OperationType operationType = executableElement.getAnnotation(Validate.class) != null ? INTERNAL_VALIDATOR_METHOD : CLASS_METHOD;
        return new FieldValidationDefinition()
                .withErrorMessageTemplate(determineErrorMessageTemplate(rawFieldDefinition, operationType))
                .withMethodName(executableElement.getSimpleName().toString())
                .withOperationType(operationType)
                .withOperationClass(null)
                .withParams(params);
    }

    private FieldValidationDefinition fromStaticMethod(String operationRef, RawFieldDefinition rawFieldDefinition, Map<String, String> params) {
        OperationConfig operationConfig = berenConfig.getOperationsMappings().get(operationRef);
        if(operationConfig != null) {
            String operationCall = operationConfig.getOperationCall();
            return new FieldValidationDefinition()
                    .withErrorMessageTemplate(determineErrorMessageTemplate(rawFieldDefinition, STATIC_METHOD))
                    .withMethodName(OperationUtils.extractMethodName(operationCall))
                    .withParams(createOperationParams(rawFieldDefinition, params, operationConfig))
                    .withOperationClass(OperationUtils.extractClassName(operationCall))
                    .withOperationType(STATIC_METHOD);
        }

        processingEnv.getMessager().printMessage(ERROR, format(OPERATION_NOT_FOUND, operationRef));
        return null;
    }

    private String determineErrorMessageTemplate(RawFieldDefinition rawFieldDefinition, FieldValidationDefinition.OperationType operationType) {
        if(isNotEmpty(rawFieldDefinition.getMessage())) {
            return rawFieldDefinition.getMessage();
        }

        return messageForOperation(rawFieldDefinition.getOperation(), operationType);
    }

    private String messageForOperation(String operationName, FieldValidationDefinition.OperationType operationType) {
        if(operationType.equals(INTERNAL_VALIDATOR_METHOD)) {
            return "";
        }

        String operation = OperationUtils.strapFromParams(operationName);
        OperationConfig operationConfig = berenConfig.getOperationsMappings().get(operation);
        if(operationConfig != null) {
            return operationConfig.getDefaultMessage();
        }

        processingEnv.getMessager().printMessage(ERROR, format(OPERATION_MESSAGE_NOT_FOUND, operation));
        return "";
    }

    private Map<String, String> createOperationParams(RawFieldDefinition rawFieldDefinition, Map<String, String> params, OperationConfig operationConfig) {
        List<String> paramValues = OperationUtils.parseParams(rawFieldDefinition.getOperation());
        List<String> argsPlaceHolders = operationConfig.getArgs();

        if(paramValues.size() == (argsPlaceHolders.size() - 1)) {
            int palceHolderIndex = 0;
            int valueIndex = 0;
            while(palceHolderIndex < argsPlaceHolders.size()) {
                String placeHolder = argsPlaceHolders.get(palceHolderIndex);
                if(!THIS_.equals(placeHolder)) {
                    params.put(placeHolder, paramValues.get(valueIndex));
                    valueIndex++;
                }
                palceHolderIndex++;
            }
            return params;
        }
        processingEnv.getMessager().printMessage(ERROR, format(OPERATION_PARAMS_NUMBER_MISMATCH, operationConfig.getOperationCall(), paramValues));
        return new HashMap<>();
    }

    private Map<String, String> mapWithThisParam(String fieldGetter, String validationParamName) {
        Map<String, String> params = new HashMap<>();
        String getterCall = validationParamName + "." + fieldGetter + "()";
        params.put(THIS_, getterCall);
        params.put(PlaceHolders.PARAM_NAME_, CodeUtils.extractFieldName(fieldGetter));

        return params;
    }
}
