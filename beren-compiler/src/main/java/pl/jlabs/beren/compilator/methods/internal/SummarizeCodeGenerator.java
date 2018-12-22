package pl.jlabs.beren.compilator.methods.internal;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;
import pl.jlabs.beren.model.ValidationResults;

import javax.lang.model.element.Modifier;

import static pl.jlabs.beren.compilator.utils.CodeUtils.VALIDATION_RESULTS_PARAM;
import static pl.jlabs.beren.compilator.utils.CodeUtils.createInternalMethodName;

public class SummarizeCodeGenerator implements StrategyCodeGenerator {

    @Override
    public MethodSpec createValidationMethod(ValidationDefinition validationDefinition) {
        String methodName = validationDefinition.getMethodName();
        String paramName = validationDefinition.getValidationParameter().getParamName();
        return MethodSpec.overriding(validationDefinition.getMethodToImplement())
                .addStatement("$T $L = new $T()", ValidationResults.class, VALIDATION_RESULTS_PARAM, ValidationResults.class)
                .addCode(createInternalMethodCall(methodName, paramName))
                .addStatement("return $L", VALIDATION_RESULTS_PARAM)
                .build();
    }

    @Override
    public MethodSpec.Builder createInternalValidationMethod(ValidationDefinition validationDefinition) {
        return MethodSpec.methodBuilder(createInternalMethodName(validationDefinition.getMethodName()))
                .addParameter(ParameterSpec.get(validationDefinition.getValidationParameter().getParam()))
                .addParameter(ValidationResults.class, VALIDATION_RESULTS_PARAM)
                .addModifiers(Modifier.PRIVATE)
                .returns(TypeName.VOID);
    }

    @Override
    public CodeBlock createNullableCodeBlock(ValidationDefinition validationDefinition, String violationMessage) {
        String paramName = validationDefinition.getValidationParameter().getParamName();
        return CodeBlock.builder()
                .beginControlFlow("if($L == null)", paramName)
                .add(validationDefinition.isNullable() ? emptyBlock() : createInvalidValueBlock(violationMessage))
                .addStatement("return")
                .endControlFlow()
                .build();
    }

    @Override
    public CodeBlock createInternalMethodCall(String methodName, String paramName) {
        String internalMethodName = createInternalMethodName(methodName);
        return CodeBlock.builder()
                .addStatement("$L($L, $L)", internalMethodName, paramName, VALIDATION_RESULTS_PARAM)
                .build();
    }

    @Override
    public CodeBlock createInvalidValueBlock(String message) {
        return CodeBlock.builder()
                .addStatement("$L.addViolation($S)", VALIDATION_RESULTS_PARAM, message)
                .build();
    }

    private CodeBlock emptyBlock() {
        return CodeBlock.builder().build();
    }
}
