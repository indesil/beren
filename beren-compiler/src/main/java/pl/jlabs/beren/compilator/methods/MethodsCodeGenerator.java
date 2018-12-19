package pl.jlabs.beren.compilator.methods;

import com.squareup.javapoet.*;
import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.compilator.configuration.BerenConfig;

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
                .addParameters(getParameters(constructor.getParameters()))
                .addStatement(createSuperConstructorCall((List<VariableElement>) constructor.getParameters()))
                .addModifiers(constructor.getModifiers())
                .build();
    }

    private List<AnnotationSpec> rewriteAnnotations(List<? extends AnnotationMirror> annotationMirrors) {
        return annotationMirrors.stream().map(annotation -> AnnotationSpec.get(annotation)).collect(toList());
    }

    private List<ParameterSpec> getParameters(List<? extends VariableElement> parameters) {
        return parameters.stream().map(param -> ParameterSpec.get(param)).collect(toList());
    }

    private CodeBlock createSuperConstructorCall(List<VariableElement> parameters) {
        String paramsStatement = parameters.stream().map(param -> param.getSimpleName().toString()).collect(Collectors.joining(","));
        return CodeBlock.builder().add("super(" + paramsStatement + ")").build();
    }

    private MethodSpec createMethod(ExecutableElement methodToImplement, TypeMethods typeMethods) {
        return MethodSpec
                .methodBuilder(methodToImplement.getSimpleName().toString())
                .addParameters(getParameters(methodToImplement.getParameters()))
                .addExceptions(getThrows(methodToImplement.getThrownTypes()))
                .addCode(writeMethodBody(methodToImplement, typeMethods))
                .addModifiers(getModifiers(methodToImplement.getModifiers()))
                .returns(getReturnType(methodToImplement))
                .build();
    }

    private Modifier[] getModifiers(Set<Modifier> methodModifiers) {
        if(methodModifiers.contains(Modifier.PUBLIC)) {
            return new Modifier[]{Modifier.PUBLIC};
        } else if(methodModifiers.contains(Modifier.PROTECTED)) {
            return new Modifier[]{Modifier.PROTECTED};
        }

        return new Modifier[0];
    }

    private List<TypeName> getThrows(List<? extends TypeMirror> thrownTypes) {
        return thrownTypes.stream().map(TypeName::get).collect(toList());
    }

    private CodeBlock writeMethodBody(ExecutableElement methodToImplement, TypeMethods typeMethods) {
        if(typeMethods.getBreakingStrategy().equals(BreakingStrategy.THROW_ON_FIRST)) {
            return CodeBlock.builder().build();
        }
        return CodeBlock.builder().addStatement("return null").build();
    }

    private TypeName getReturnType(ExecutableElement methodToImplement) {
        return TypeName.get(methodToImplement.getReturnType());
    }
}
