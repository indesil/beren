package pl.indesil.beren.compilator.methods.internal;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import pl.indesil.beren.compilator.parser.GetterDefinition;
import pl.indesil.beren.compilator.parser.IterationType;
import pl.indesil.beren.annotations.BreakingStrategy;
import pl.indesil.beren.compilator.parser.FieldValidationDefinition;
import pl.indesil.beren.compilator.parser.ValidationDefinition;
import pl.indesil.beren.operations.CollectionUtils;

import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static pl.indesil.beren.compilator.parser.FieldValidationDefinition.OperationType.CLASS_METHOD;
import static pl.indesil.beren.compilator.parser.FieldValidationDefinition.OperationType.INTERNAL_VALIDATOR_METHOD;
import static pl.indesil.beren.compilator.parser.PlaceHolders.INPUT_;
import static pl.indesil.beren.compilator.parser.PlaceHolders.THIS_;
import static pl.indesil.beren.compilator.utils.CodeUtils.*;
import static pl.indesil.beren.operations.CollectionUtils.COLLECTION_OR_EMPTY;
import static pl.indesil.beren.operations.CollectionUtils.MAP_OR_EMPTY;

public class InternalMethodGenerator {

    private static final String KEY_SET_CALL = ".keySet()";
    private static final String VALUES_CALL = ".values()";
    private StrategyCodeGenerator strategyCodeGenerator;

    public InternalMethodGenerator(BreakingStrategy breakingStrategy) {
        if(breakingStrategy.equals(BreakingStrategy.THROW_ON_FIRST)) {
            strategyCodeGenerator = new ThrowOnFirstCodeGenerator();
        } else {
            strategyCodeGenerator = new SummarizeCodeGenerator();
        }
    }

    public MethodSpec createValidationMethod(ValidationDefinition validationDefinition) {
        return strategyCodeGenerator.createValidationMethod(validationDefinition);
    }

    public MethodSpec createInternalValidationMethod(ValidationDefinition validationDefinition) {
        return strategyCodeGenerator
                .createInternalValidationMethod(validationDefinition)
                .addCode(generateMethodCode(validationDefinition))
                .build();
    }

    private CodeBlock generateMethodCode(ValidationDefinition validationDefinition) {
        return CodeBlock.builder()
                .add(createNullableCodeBlock(validationDefinition))
                .add(createValidationBlocks(validationDefinition))
                .build();
    }

    private CodeBlock createNullableCodeBlock(ValidationDefinition validationDefinition) {
        String paramName = validationDefinition.getValidatedParamName();
        String violationMessage = formatErrorMessage(validationDefinition.getNullableMessageTemplate(), INPUT_, paramName);
        return strategyCodeGenerator.createNullableCodeBlock(validationDefinition, violationMessage);
    }

    private CodeBlock createValidationBlocks(ValidationDefinition validationDefinition) {
        CodeBlock.Builder builder = CodeBlock.builder();
        validationDefinition.getFieldsToValidate().forEach(fieldDefinition -> builder.add(createFieldValidationBlock(fieldDefinition)));
        return builder.build();
    }

    private CodeBlock createFieldValidationBlock(FieldValidationDefinition fieldDefinition) {
        CodeBlock.Builder builder = createProperBuilder(fieldDefinition);

        if(fieldDefinition.getOperationType().equals(INTERNAL_VALIDATOR_METHOD)) {
            builder.add(createInternalValidatorCall(fieldDefinition));
        } else if(fieldDefinition.getOperationType().equals(CLASS_METHOD)) {
            builder.add(createValidationByClassMethodBlock(fieldDefinition));
        } else {
            builder.add(createValidationIfBlock(fieldDefinition));
        }

        if(!fieldDefinition.getIterationType().equals(IterationType.NONE)) {
            builder.endControlFlow();
        }

        return builder.build();
    }

    //tutaj brakuje messagow jezeli cos pojdzie nie tak
    // w ogole to kontrola typow jest raczej slaba tutaj
    private CodeBlock.Builder createProperBuilder(FieldValidationDefinition fieldDefinition) {
        if(fieldDefinition.getIterationType().equals(IterationType.NONE)) {
            return CodeBlock.builder();
        }
        TypeMirror valueType = extractValueType(fieldDefinition);
        if(valueType != null) {
            String getterCall = createGetterCall(fieldDefinition);
            ClassName collectionUtils = ClassName.get(CollectionUtils.class);
            TypeName argumentType = ClassName.get(valueType);
            fieldDefinition.getParams().put(THIS_, ITERATION_PARAM);
            return CodeBlock.builder()
                    .beginControlFlow("for( $T $L : $T.$L)", argumentType, ITERATION_PARAM,
                            collectionUtils, getterCall);
        }

        return CodeBlock.builder();
    }

    private TypeMirror extractValueType(FieldValidationDefinition fieldDefinition) {
        List<? extends TypeMirror> genericTypes = fieldDefinition.getGetterDefinition().getGenericTypes();
        if(genericTypes.isEmpty()) {
            return null;
        }

        if(fieldDefinition.getIterationType().equals(IterationType.EACH_MAP_KEY)) {
            return genericTypes.get(0);
        }
        if(isMapValues(fieldDefinition)) {
            return genericTypes.get(1);
        }

        return genericTypes.get(0);
    }

    private boolean isMapValues(FieldValidationDefinition fieldDefinition) {
        return fieldDefinition.getIterationType().equals(IterationType.EACH_VALUE) && fieldDefinition.getGetterDefinition().getReturnType().equals(GetterDefinition.ReturnType.MAP);
    }

    private String createGetterCall(FieldValidationDefinition fieldDefinition) {
        String getterCall = fieldDefinition.getGetterDefinition().getGetterCall();
        if(fieldDefinition.getIterationType().equals(IterationType.EACH_MAP_KEY)) {
            return wrapInCollectionUtilCall(MAP_OR_EMPTY, getterCall) + KEY_SET_CALL;
        }

        if(isMapValues(fieldDefinition)) {
            return wrapInCollectionUtilCall(MAP_OR_EMPTY, getterCall) + VALUES_CALL;
        }

        return wrapInCollectionUtilCall(COLLECTION_OR_EMPTY, getterCall);
    }

    private String wrapInCollectionUtilCall(String collectionUtilWrapper, String getterCall) {
        return collectionUtilWrapper + "(" + getterCall + ")";
    }

    private CodeBlock createInternalValidatorCall(FieldValidationDefinition fieldDefinition) {
        String fieldGetter = fieldDefinition.getParams().get(THIS_);
        return CodeBlock.builder()
                .add(strategyCodeGenerator.createInternalMethodCall(fieldDefinition.getMethodName(), fieldGetter))
                .build();
    }

    private CodeBlock createValidationByClassMethodBlock(FieldValidationDefinition fieldDefinition) {
        String methodCall = fieldDefinition.getMethodName();
        String violationMessage = createViolationMessage(fieldDefinition);
        String args = methodArguments(fieldDefinition);
        return CodeBlock.builder()
                .beginControlFlow("if(!$L($L))", methodCall, args)
                .add(strategyCodeGenerator.createInvalidValueBlock(violationMessage))
                .endControlFlow()
                .build();
    }

    private String methodArguments(FieldValidationDefinition fieldDefinition) {
        Map<String, String> params = fieldDefinition.getParams();
        return fieldDefinition.getParamsOrder().stream()
                .map(paramPlaceHolder -> createMethodArgument(params.get(paramPlaceHolder)))
                .collect(joining(","));
    }

    private String createViolationMessage(FieldValidationDefinition fieldDefinition) {
        return createViolationMessage(fieldDefinition.getErrorMessageTemplate(), fieldDefinition.getParams());
    }

    private String createViolationMessage(String errorMessageTemplate, Map<String, String> params) {
        if(isNotEmpty(errorMessageTemplate)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                errorMessageTemplate = formatErrorMessage(errorMessageTemplate, entry.getKey(), entry.getValue());
            }
        }
        return errorMessageTemplate;
    }

    private String formatErrorMessage(String errorMessageTemplate, String placeHolder, String value) {
        String templatePlaceHolder = createTemplatePlaceHolder(placeHolder);
        String placeHolderValue = createPlaceHolderParams(value);
        return errorMessageTemplate.replaceAll(templatePlaceHolder, placeHolderValue);
    }

    private CodeBlock createValidationIfBlock(FieldValidationDefinition fieldDefinition) {
        ClassName operationCallClass = fieldDefinition.getOperationClass();
        String methodCall = fieldDefinition.getMethodName();
        String violationMessage = createViolationMessage(fieldDefinition);
        String args = methodArguments(fieldDefinition);
        return CodeBlock.builder()
                .beginControlFlow("if(!$T.$L($L))", operationCallClass, methodCall, args)
                .add(strategyCodeGenerator.createInvalidValueBlock(violationMessage))
                .endControlFlow()
                .build();
    }
}
