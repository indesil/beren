package pl.jlabs.beren.compilator.utils;

import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.configuration.OperationConfig;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.constructorsIn;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.MANDATORY_WARNING;
import static pl.jlabs.beren.compilator.utils.CodeUtils.createValidatorName;

public class ProcessingFacade {
    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;

    public ProcessingFacade(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
    }

    public void warning(String format, Object... params) {
        String warningMessage = format(format, params);
        processingEnv.getMessager().printMessage(MANDATORY_WARNING, warningMessage);
    }

    public void error(String format, Object... params) {
        String warningMessage = format(format, params);
        processingEnv.getMessager().printMessage(ERROR, warningMessage);
    }

    public TypeMirror getElementType(Class cl) {
        return processingEnv.getElementUtils().getTypeElement(cl.getName()).asType();
    }

    public List<ExecutableElement> extractTypeMethods(TypeElement typeElement) {
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
        return extractMethods(allMembers);
    }

    public List<ExecutableElement> extractTypeConstructors(TypeElement typeElement) {
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
        return constructorsIn(allMembers);
    }

    public List<ExecutableElement> extractGetterMethods(VariableElement validationParam) {
        TypeElement paramElement = (TypeElement) processingEnv.getTypeUtils().asElement(validationParam.asType());
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(paramElement);
        return extractMethods(allMembers)
                .stream()
                .filter(MethodsUtil::isGetterMethod)
                .collect(toList());
    }

    public boolean isAssignable(TypeMirror getterReturnType, TypeMirror definitionType) {
        return processingEnv.getTypeUtils().isAssignable(processingEnv.getTypeUtils().erasure(getterReturnType), definitionType);
    }

    public OperationConfig getOperationMapping(String operationRef) {
        return berenConfig.getOperationsMappings().get(operationRef);
    }

    public PackageElement getPackageOfType(Element typeElement) {
        return processingEnv.getElementUtils().getPackageOf(typeElement);
    }

    private List<ExecutableElement> extractMethods(List<? extends Element> allTypeMembers) {
        List<ExecutableElement> allClassDefinedMethods = methodsIn(allTypeMembers);
        allClassDefinedMethods.removeAll(objectMethods());

        return allClassDefinedMethods;
    }

    private List<ExecutableElement> objectMethods() {
        TypeElement objectElement = processingEnv.getElementUtils().getTypeElement(Object.class.getName());
        return methodsIn(objectElement.getEnclosedElements());
    }

    public JavaFileObject createJavaSourceFileForType(String javaClassName, Element typeElement) {
        try {
            return processingEnv.getFiler().createSourceFile(javaClassName, typeElement);
        } catch (IOException e) {
            error("Error while generating validator class %s",  e.getMessage());
        }

        return null;
    }
}
