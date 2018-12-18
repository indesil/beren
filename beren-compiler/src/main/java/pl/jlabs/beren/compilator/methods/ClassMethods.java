package pl.jlabs.beren.compilator.methods;

import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassMethods {
    private List<ExecutableElement> methodsToImplement = new ArrayList<>();
    private Map<String, ExecutableElement> methodsReferences = new HashMap<>();

    public List<ExecutableElement> getMethodsToImplement() {
        return methodsToImplement;
    }

    public void addMethodToImplement(ExecutableElement methodElement) {
        methodsToImplement.add(methodElement);
    }

    public void addMethodReference(String methodName, ExecutableElement methodElement) {
        methodsReferences.put(methodName, methodElement);
    }

    public ExecutableElement getMethod(String methodName) {
        return methodsReferences.get(methodName);
    }
}
