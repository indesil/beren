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
    public MethodSpec.Builder createInternalValidationMethod(ValidationDefinition validationDefinition) {
        return MethodSpec.methodBuilder(createInternalMethodName(validationDefinition.getMethodName()))
                .addParameter(ParameterSpec.get(validationDefinition.getParameter()))
                .addParameter(ValidationResults.class, VALIDATION_RESULTS_PARAM)
                .addModifiers(Modifier.PRIVATE)
                .returns(TypeName.VOID);
    }

    @Override
    public CodeBlock createInternalMethodCall(ValidationDefinition validationDefinition) {
        String internalMethodName = createInternalMethodName(validationDefinition.getMethodName());
        String validationMethodParamName = validationDefinition.getParameter().getSimpleName().toString();
        return CodeBlock.builder()
                .addStatement("$T $L = new $T()", ValidationResults.class, VALIDATION_RESULTS_PARAM, ValidationResults.class)
                .addStatement("$L($L, $L)", internalMethodName, validationMethodParamName, VALIDATION_RESULTS_PARAM)
                .addStatement("return $L", VALIDATION_RESULTS_PARAM)
                .build();
    }

    @Override
    public CodeBlock createInvalidValueBlock(String message) {
        return CodeBlock.builder()
                .addStatement("$L.addViolation($S)", VALIDATION_RESULTS_PARAM, message)
                .addStatement("return")
                .build();
    }
}
