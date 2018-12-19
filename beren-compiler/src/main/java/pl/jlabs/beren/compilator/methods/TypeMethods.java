package pl.jlabs.beren.compilator.methods;

import pl.jlabs.beren.annotations.BreakingStrategy;

import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeMethods {
    private List<ExecutableElement> methodsToImplement = new ArrayList<>();
    private Map<String, ExecutableElement> methodsReferences = new HashMap<>();
    private List<ExecutableElement> constructors;
    private BreakingStrategy breakingStrategy;

    public TypeMethods(List<ExecutableElement> constructors, BreakingStrategy breakingStrategy) {
        this.constructors = constructors != null ? constructors : new ArrayList<>();
        this.breakingStrategy = breakingStrategy;
    }

    public List<ExecutableElement> getConstructors() {
        return constructors;
    }

    public List<ExecutableElement> getMethodsToImplement() {
        return methodsToImplement;
    }

    public void addMethodToImplement(ExecutableElement methodElement) {
        methodsToImplement.add(methodElement);
    }

    public void addMethodReference(String methodName, ExecutableElement methodElement) {
        methodsReferences.put(methodName, methodElement);
    }

    public ExecutableElement getMethod(String methodReference) {
        return methodsReferences.get(methodReference);
    }

    public BreakingStrategy getBreakingStrategy() {
        return breakingStrategy;
    }
}
