package pl.indesil.beren.compilator.methods.internal;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import pl.indesil.beren.compilator.parser.ValidationDefinition;
import pl.indesil.beren.exceptions.ValidationException;

import javax.lang.model.element.Modifier;

import static pl.indesil.beren.compilator.utils.CodeUtils.createInternalMethodName;

public class ThrowOnFirstCodeGenerator implements StrategyCodeGenerator {

    @Override
    public MethodSpec createValidationMethod(ValidationDefinition validationDefinition) {
        String methodName = validationDefinition.getMethodToImplementName();
        String paramName = validationDefinition.getValidatedParamName();
        return MethodSpec.overriding(validationDefinition.getMethodToImplement())
                .addCode(createInternalMethodCall(methodName, paramName))
                .build();
    }

    @Override
    public MethodSpec.Builder createInternalValidationMethod(ValidationDefinition validationDefinition) {
        return MethodSpec.methodBuilder(createInternalMethodName(validationDefinition.getMethodToImplementName()))
                .addParameter(ParameterSpec.get(validationDefinition.getValidatedParam()))
                .addModifiers(Modifier.PRIVATE)
                .returns(TypeName.VOID);
    }

    @Override
    public CodeBlock createNullableCodeBlock(ValidationDefinition validationDefinition, String violationMessage) {
        String paramName = validationDefinition.getValidatedParamName();
        return CodeBlock.builder()
                .beginControlFlow("if($L == null)", paramName)
                .add(validationDefinition.isNullable() ? voidReturn() : createInvalidValueBlock(violationMessage))
                .endControlFlow()
                .build();
    }

    @Override
    public CodeBlock createInternalMethodCall(String methodName, String paramName) {
        String internalMethodName = createInternalMethodName(methodName);
        return CodeBlock.builder()
                .addStatement("$L($L)", internalMethodName, paramName)
                .build();
    }

    @Override
    public CodeBlock createInvalidValueBlock(String message) {
        return CodeBlock.builder().addStatement("throw new $T($S)", ValidationException.class, message).build();
    }

    private CodeBlock voidReturn() {
        return CodeBlock.builder().addStatement("return").build();
    }
}
