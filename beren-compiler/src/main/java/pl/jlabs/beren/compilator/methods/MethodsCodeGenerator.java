package pl.jlabs.beren.compilator.methods;

import com.squareup.javapoet.*;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.model.ValidationResults;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MethodsCodeGenerator {

    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;

    public MethodsCodeGenerator(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
    }

    public List<MethodSpec> generateMethodsCode(TypeMethods typeMethods) {
        List<MethodSpec> methods = new ArrayList<>();

        for (ExecutableElement constructor : typeMethods.getConstructors()) {
            if(!constructor.getModifiers().contains(Modifier.PRIVATE)) {
                methods.add(createConstructor(constructor));
            }
        }

        for (ExecutableElement methodToImplement : typeMethods.getMethodsToImplement()) {
            methods.add(createMethod(methodToImplement, typeMethods));
        }

        return methods;
    }

    private MethodSpec createConstructor(ExecutableElement constructor) {
        return MethodSpec.constructorBuilder()
                .addAnnotations(rewriteAnnotations(constructor.getAnnotationMirrors()))
                .addParameters(rewriteParameters(constructor.getParameters()))
                .addStatement(createSuperConstructorCall((List<VariableElement>) constructor.getParameters()))
                .addModifiers(constructor.getModifiers())
                .build();
    }

    private List<AnnotationSpec> rewriteAnnotations(List<? extends AnnotationMirror> annotationMirrors) {
        return annotationMirrors.stream().map(annotation -> AnnotationSpec.get(annotation)).collect(toList());
    }

    private List<ParameterSpec> rewriteParameters(List<? extends VariableElement> parameters) {
        return parameters.stream().map(param -> ParameterSpec.get(param)).collect(toList());
    }

    private CodeBlock createSuperConstructorCall(List<VariableElement> parameters) {
        String paramsStatement = parameters.stream().map(param -> param.getSimpleName().toString()).collect(Collectors.joining(","));
        return CodeBlock.builder().add("super(" + paramsStatement + ")").build();
    }

    private MethodSpec createMethod(ExecutableElement methodToImplement, TypeMethods typeMethods) {
        MethodSpec.Builder builder = MethodSpec
                .methodBuilder(methodToImplement.getSimpleName().toString())
                .returns(ValidationResults.class);

        addModifiers(methodToImplement.getModifiers(), builder);
        addParameters(methodToImplement.getParameters(), builder);
        addThrows(methodToImplement.getThrownTypes(), builder);
        addCodeBlock(methodToImplement, typeMethods, builder);

        return builder.build();
    }

    private void addModifiers(Set<Modifier> methodModifiers, MethodSpec.Builder builder) {
        if(methodModifiers.contains(Modifier.PUBLIC)) {
            builder.addModifiers(Modifier.PUBLIC);
        } else if(methodModifiers.contains(Modifier.PROTECTED)) {
            builder.addModifiers(Modifier.PROTECTED);
        }
    }

    private void addParameters(List<? extends VariableElement> parameters, MethodSpec.Builder builder) {
        parameters.forEach(param -> builder.addParameter(ParameterSpec.get(param)));
    }

    private void addThrows(List<? extends TypeMirror> thrownTypes, MethodSpec.Builder builder) {
        thrownTypes.forEach(exception -> builder.addException(TypeName.get(exception)));
    }

    private void addCodeBlock(ExecutableElement methodToImplement, TypeMethods typeMethods, MethodSpec.Builder builder) {
        builder.addStatement("return null");
    }
}
