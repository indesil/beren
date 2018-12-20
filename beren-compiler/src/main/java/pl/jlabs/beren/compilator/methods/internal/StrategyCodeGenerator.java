package pl.jlabs.beren.compilator.methods.internal;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;

public interface StrategyCodeGenerator {
    MethodSpec.Builder createInternalValidationMethod(ValidationDefinition validationDefinition);

    CodeBlock createInternalMethodCall(ValidationDefinition validationDefinition);

    CodeBlock createInvalidValueBlock(String message);
}
