package pl.jlabs.beren.compilator.parser.source;

import org.apache.commons.lang3.tuple.Pair;
import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.configuration.OperationConfig;
import pl.jlabs.beren.compilator.parser.*;
import pl.jlabs.beren.compilator.utils.CodeUtils;
import pl.jlabs.beren.compilator.utils.OperationUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static javax.tools.Diagnostic.Kind.ERROR;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static pl.jlabs.beren.compilator.parser.FieldValidationDefinition.OperationType.*;
import static pl.jlabs.beren.compilator.parser.PlaceHolders.PARAM_NAME_;
import static pl.jlabs.beren.compilator.parser.PlaceHolders.THIS_;
import static pl.jlabs.beren.compilator.utils.CodeUtils.extractFieldName;
import static pl.jlabs.beren.compilator.utils.CodeUtils.isNotVoidType;
import static pl.jlabs.beren.compilator.utils.DefinitionUtils.createRawFieldDefinition;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.*;
import static pl.jlabs.beren.compilator.utils.OperationUtils.parseOperation;
import static pl.jlabs.beren.compilator.utils.OperationUtils.strapFromParams;

public class MethodAnnotationSourceParser implements SourceParser {
    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;
    private TypeMirror mapType;
    private TypeMirror collectionType;

    public MethodAnnotationSourceParser(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
        mapType = processingEnv.getElementUtils().getTypeElement("java.util.Map").asType();
        collectionType = processingEnv.getElementUtils().getTypeElement("java.util.Collection").asType();
    }

    @Override
    public ValidationDefinition parse(ParserContext parserContext) {
        Validate validateAnnotation = parserContext.getMethodToImplement().getAnnotation(Validate.class);
        return new ValidationDefinition()
                .withNullable(validateAnnotation.nullable())
                .withMethodToImplement(parserContext.getMethodToImplement())
                .withNullableMessageTemplate(validateAnnotation.nullableMessage())
                .withFieldsToValidate(createFieldsDefinitions(validateAnnotation.value(), parserContext))
                .withValidatedParam(parserContext.getValidationMethodParam());
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
                List<FieldValidationDefinition> fieldsDefinition = createDefinitionsForField(rawFieldDefinition, parserContext);
                if(fieldsDefinition.isEmpty()) {
                    processingEnv.getMessager().printMessage(ERROR, format(VALIDATION_DEFINITION_FAILED, rawFieldDefinition));
                }
                fieldValidationDefinitions.addAll(fieldsDefinition);
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
        ExecutableElement fieldGetter = findFieldGetter(rawFieldDefinition.getName(), parserContext);
        if(fieldGetter != null) {
            return singletonList(createFieldDefinition(rawFieldDefinition, parserContext, fieldGetter));
        }
        return emptyList();
    }

    private List<FieldValidationDefinition> createDefinitionForMultipleNames(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        return Stream.of(rawFieldDefinition.getNames())
                .map(fieldName -> findFieldGetter(fieldName, parserContext))
                .filter(Objects::nonNull)
                .map(fieldGetter -> createFieldDefinition(rawFieldDefinition, parserContext, fieldGetter))
                .collect(toList());
    }

    private List<FieldValidationDefinition> createDefinitionFromPattern(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        Pattern pattern = rawFieldDefinition.getPattern();
        return parserContext.getValidationParamFieldsGetters().stream()
                .filter(getter -> pattern.matcher(extractFieldName(getter.getSimpleName().toString())).matches())
                .map(getterName -> createFieldDefinition(rawFieldDefinition, parserContext, getterName))
                .collect(toList());
    }

    private List<FieldValidationDefinition> createDefinitionFromType(RawFieldDefinition rawFieldDefinition, ParserContext parserContext) {
        return parserContext.getValidationParamFieldsGetters().stream()
                .filter(getter -> isAssignable(getter.getReturnType(), rawFieldDefinition.getType()))
                .map(getter -> createFieldDefinition(rawFieldDefinition, parserContext, getter))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private boolean isAssignable(TypeMirror getterReturnType, TypeMirror definitionType) {
        return processingEnv.getTypeUtils().isAssignable(processingEnv.getTypeUtils().erasure(getterReturnType), definitionType);
    }

    private FieldValidationDefinition createFieldDefinition(RawFieldDefinition rawFieldDefinition, ParserContext parserContext, ExecutableElement getterMethod) {
        Pair<IterationType, String> operation = parseOperation(rawFieldDefinition.getOperation());
        if(operation != null) {
            String operationRef = strapFromParams(operation.getValue());
            ExecutableElement executableElement = parserContext.getMethodReferences().get(operationRef);
            Map<String, String> params = paramsMap(getterMethod.getSimpleName().toString(), parserContext.getValidationMethodParamName());
            if(executableElement != null) {
                return fromClassMethodRef(executableElement, rawFieldDefinition, params, operation.getKey(), getterMethod);
            }

            return fromStaticMethod(operationRef, rawFieldDefinition, params, operation.getKey(), getterMethod);
        }

        processingEnv.getMessager().printMessage(ERROR, format(INVALID_OPERATION, rawFieldDefinition.getOperation()));
        return null;
    }

    private ExecutableElement findFieldGetter(String fieldName, ParserContext parserContext) {
        String lowercaseFieldName = fieldName.toLowerCase();
        Optional<ExecutableElement> fieldGetter = parserContext.getValidationParamFieldsGetters()
                .stream()
                .filter(getter -> CodeUtils.normalizeGetterName(getter.getSimpleName().toString()).equals(lowercaseFieldName))
                .findFirst();
        if(fieldGetter.isPresent()) {
            return fieldGetter.get();
        }
        processingEnv.getMessager().printMessage(ERROR, format(FIELD_GETTER_NOT_FOUND, parserContext.getValidationMethodParamName(), fieldName));
        return null;
    }

    private FieldValidationDefinition fromClassMethodRef(ExecutableElement executableElement,
                                                         RawFieldDefinition rawFieldDefinition,
                                                         Map<String, String> params,
                                                         IterationType iterationType,
                                                         ExecutableElement getterMethod) {
        FieldValidationDefinition.OperationType operationType = executableElement.getAnnotation(Validate.class) != null ? INTERNAL_VALIDATOR_METHOD : CLASS_METHOD;
        return new FieldValidationDefinition()
                .withErrorMessageTemplate(determineErrorMessageTemplate(rawFieldDefinition, operationType))
                .withMethodName(executableElement.getSimpleName().toString())
                .withOperationType(operationType)
                .withOperationClass(null)
                .withGetterDefinition(createGetterDefinition(getterMethod, params))
                .withIterationType(iterationType)
                .withParamsOrder(singletonList(THIS_))
                .withParams(params);
    }

    private FieldValidationDefinition fromStaticMethod(String operationRef,
                                                       RawFieldDefinition rawFieldDefinition,
                                                       Map<String, String> params,
                                                       IterationType iterationType,
                                                       ExecutableElement getterMethod) {
        OperationConfig operationConfig = berenConfig.getOperationsMappings().get(operationRef);
        if(operationConfig != null) {
            String operationCall = operationConfig.getOperationCall();
            return new FieldValidationDefinition()
                    .withErrorMessageTemplate(determineErrorMessageTemplate(rawFieldDefinition, STATIC_METHOD))
                    .withMethodName(OperationUtils.extractMethodName(operationCall))
                    .withParams(createOperationParams(rawFieldDefinition, params, operationConfig))
                    .withIterationType(iterationType)
                    .withOperationClass(OperationUtils.extractClassName(operationCall))
                    .withGetterDefinition(createGetterDefinition(getterMethod, params))
                    .withParamsOrder(operationConfig.getArgs())
                    .withOperationType(STATIC_METHOD);
        }

        processingEnv.getMessager().printMessage(ERROR, format(OPERATION_NOT_FOUND, operationRef));
        return null;
    }

    private GetterDefinition createGetterDefinition(ExecutableElement getterMethod, Map<String, String> params) {
        return new GetterDefinition()
                .withGetterCall(params.get(THIS_))
                .withReturnType(extractReturnType(getterMethod.getReturnType()))
                .withGenericTypes(extractGenericTypes(getterMethod.getReturnType()));
    }

    private GetterDefinition.ReturnType extractReturnType(TypeMirror returnType) {
        if(processingEnv.getTypeUtils().isAssignable(processingEnv.getTypeUtils().erasure(returnType), mapType)) {
            return GetterDefinition.ReturnType.MAP;
        }

        if(processingEnv.getTypeUtils().isAssignable(processingEnv.getTypeUtils().erasure(returnType), collectionType)) {
            return GetterDefinition.ReturnType.COLLECTION;
        }

        return GetterDefinition.ReturnType.SINGLE_FIELD;
    }

    private List<? extends TypeMirror> extractGenericTypes(TypeMirror returnType) {
        if(returnType instanceof DeclaredType) {
            return ((DeclaredType)returnType).getTypeArguments();
        }
        return emptyList();
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

        String operation = strapFromParams(operationName);
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

    private Map<String, String> paramsMap(String fieldGetter, String validationParamName) {
        Map<String, String> params = new HashMap<>();
        String getterCall = validationParamName + "." + fieldGetter + "()";
        params.put(THIS_, getterCall);
        params.put(PARAM_NAME_, CodeUtils.extractFieldName(fieldGetter));

        return params;
    }
}
