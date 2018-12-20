package pl.jlabs.beren.compilator.methods;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;

import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeMetadata {
    private Map<String, ExecutableElement> methodsReferences = new HashMap<>();
    private List<ExecutableElement> constructors;
    private BreakingStrategy breakingStrategy;
    private List<ValidationDefinition> validationDefinitions = new ArrayList<>();

    public TypeMetadata(List<ExecutableElement> constructors, BreakingStrategy breakingStrategy) {
        this.constructors = constructors != null ? constructors : new ArrayList<>();
        this.breakingStrategy = breakingStrategy;
    }

    public List<ExecutableElement> getConstructors() {
        return constructors;
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

    public void addValidationDefinition(ValidationDefinition validationDefinition) {
        validationDefinitions.add(validationDefinition);
    }

    public List<ValidationDefinition> getValidationDefinitions() {
        return validationDefinitions;
    }
}
