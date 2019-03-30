package io.github.indesil.beren.compilator.methods.internal;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import io.github.indesil.beren.compilator.parser.ValidationDefinition;

public interface StrategyCodeGenerator {
    MethodSpec createValidationMethod(ValidationDefinition validationDefinition);

    MethodSpec.Builder createInternalValidationMethod(ValidationDefinition validationDefinition);

    CodeBlock createNullableCodeBlock(ValidationDefinition validationDefinition, String violationMessage);

    CodeBlock createInternalMethodCall(String methodName, String paramName);

    CodeBlock createInvalidValueBlock(String message);
}
