package pl.jlabs.beren.compilator.methods;

import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;

import javax.lang.model.element.ExecutableElement;
import java.util.*;

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

    public Optional<ExecutableElement> getMethod(String methodReference) {
        return methodsReferences.entrySet().stream()
                .filter(entry -> entry.getKey().equals(methodReference))
                .map(Map.Entry::getValue)
                .findAny();
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
