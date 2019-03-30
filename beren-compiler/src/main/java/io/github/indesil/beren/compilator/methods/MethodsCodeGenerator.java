package io.github.indesil.beren.compilator.methods;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import io.github.indesil.beren.compilator.methods.internal.InternalMethodGenerator;
import io.github.indesil.beren.compilator.parser.ValidatorDefinition;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MethodsCodeGenerator {

    public static List<MethodSpec> generateMethodsCode(ValidatorDefinition validatorDefinition) {
        List<MethodSpec> methods = new ArrayList<>();
        validatorDefinition.getConstructors().stream()
                .filter(constructor -> !constructor.getModifiers().contains(Modifier.PRIVATE))
                .forEach(constructor -> methods.add(createConstructor(constructor)));

        InternalMethodGenerator internalMethodGenerator = new InternalMethodGenerator(validatorDefinition.getBreakingStrategy());
        validatorDefinition.getValidationDefinitions()
                .forEach(validationDefinition -> {
                    methods.add(internalMethodGenerator.createValidationMethod(validationDefinition));
                    methods.add(internalMethodGenerator.createInternalValidationMethod(validationDefinition));
                });

        return methods;
    }

    private static MethodSpec createConstructor(ExecutableElement constructor) {
        return MethodSpec.constructorBuilder()
                .addAnnotations(rewriteAnnotations(constructor.getAnnotationMirrors()))
                .addParameters(getParameters(constructor.getParameters()))
                .addStatement(createSuperConstructorCall(constructor.getParameters()))
                .addModifiers(constructor.getModifiers())
                .build();
    }

    private static List<AnnotationSpec> rewriteAnnotations(List<? extends AnnotationMirror> annotationMirrors) {
        return annotationMirrors.stream().map(AnnotationSpec::get).collect(toList());
    }

    private static List<ParameterSpec> getParameters(List<? extends VariableElement> parameters) {
        return parameters.stream().map(ParameterSpec::get).collect(toList());
    }

    private static CodeBlock createSuperConstructorCall(List<? extends VariableElement> parameters) {
        String paramsStatement = parameters.stream().map(param -> param.getSimpleName().toString()).collect(Collectors.joining(","));
        return CodeBlock.builder().add("super(" + paramsStatement + ")").build();
    }
}
