package pl.jlabs.beren.compilator.methods.internal;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;
import pl.jlabs.beren.exceptions.ValidationException;

import javax.lang.model.element.Modifier;

import static pl.jlabs.beren.compilator.utils.CodeUtils.createInternalMethodName;

public class ThrowOnFirstCodeGenerator implements StrategyCodeGenerator {
    @Override
    public MethodSpec.Builder createInternalValidationMethod(ValidationDefinition validationDefinition) {
        return MethodSpec.methodBuilder(createInternalMethodName(validationDefinition.getMethodName()))
                .addParameter(ParameterSpec.get(validationDefinition.getParameter()))
                .addModifiers(Modifier.PRIVATE)
                .returns(TypeName.VOID);
    }

    @Override
    public CodeBlock createInternalMethodCall(ValidationDefinition validationDefinition) {
        String internalMethodName = createInternalMethodName(validationDefinition.getMethodName());
        String validationMethodParamName = validationDefinition.getParameter().getSimpleName().toString();
        return CodeBlock.builder()
                .addStatement("$L($L)", internalMethodName, validationMethodParamName)
                .addStatement("return")
                .build();
    }

    @Override
    public CodeBlock createInvalidValueBlock(String message) {
        return CodeBlock.builder().addStatement("throw new $T($S)", ValidationException.class, message).build();
    }
}
