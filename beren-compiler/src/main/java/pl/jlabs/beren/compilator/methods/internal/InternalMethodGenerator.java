package pl.jlabs.beren.compilator.methods.internal;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.compilator.parser.FieldValidationDefinition;
import pl.jlabs.beren.compilator.parser.ValidationDefinition;
import pl.jlabs.beren.compilator.utils.CodeUtils;

import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static pl.jlabs.beren.compilator.parser.FieldValidationDefinition.OperationType.CLASS_METHOD;
import static pl.jlabs.beren.compilator.parser.FieldValidationDefinition.OperationType.INTERNAL_VALIDATOR_METHOD;
import static pl.jlabs.beren.compilator.parser.PlaceHolders.PARAM_NAME;
import static pl.jlabs.beren.compilator.parser.PlaceHolders.THIS_;
import static pl.jlabs.beren.compilator.utils.CodeUtils.createPlaceHolderParams;
import static pl.jlabs.beren.compilator.utils.CodeUtils.createTemplatePlaceHolder;

public class InternalMethodGenerator {

    private Pattern PARAM_NAME_PATTERN = Pattern.compile(PARAM_NAME);
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
        String violationMessage = formatViolationMessage(validationDefinition.getNullableMessageTemplate(), paramName);
        return strategyCodeGenerator.createNullableCodeBlock(validationDefinition, violationMessage);
    }

    private String formatViolationMessage(String message, String paramName) {
        return PARAM_NAME_PATTERN.matcher(message).replaceAll(paramName);
    }

    private CodeBlock createValidationBlocks(ValidationDefinition validationDefinition) {
        CodeBlock.Builder builder = CodeBlock.builder();
        for (FieldValidationDefinition fieldDefinition : validationDefinition.getFieldsToValidate()) {
            builder.add(createFieldValidationBlock(fieldDefinition));
        }

        return builder.build();
    }

    private CodeBlock createFieldValidationBlock(FieldValidationDefinition fieldDefinition) {
        CodeBlock.Builder builder = CodeBlock.builder();
        if(fieldDefinition.getOperationType().equals(INTERNAL_VALIDATOR_METHOD)) {
            builder.add(createInternalValidatorCall(fieldDefinition));
        } else if(fieldDefinition.getOperationType().equals(CLASS_METHOD)) {
            builder.add(createValidationByClassMethodBlock(fieldDefinition));
        } else {
            builder.add(createValidationIfBlock(fieldDefinition));
        }

        return builder.build();
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
                .map(param -> CodeUtils.createMethodArgument(params.get(param)))
                .collect(joining(","));
    }

    private String createViolationMessage(FieldValidationDefinition fieldDefinition) {
        String errorMessageTemplate = fieldDefinition.getErrorMessageTemplate();
        if(isNotEmpty(errorMessageTemplate)) {
            for (Map.Entry<String, String> entry : fieldDefinition.getParams().entrySet()) {
                String templatePlaceHolder = createTemplatePlaceHolder(entry.getKey());
                String placeHolderValue = createPlaceHolderParams(entry.getValue());
                errorMessageTemplate = errorMessageTemplate.replaceAll(templatePlaceHolder, placeHolderValue);
            }
        }
        return errorMessageTemplate;
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
