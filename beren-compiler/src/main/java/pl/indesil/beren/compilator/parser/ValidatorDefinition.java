package pl.indesil.beren.compilator.parser;

import pl.indesil.beren.annotations.BreakingStrategy;

import javax.lang.model.element.ExecutableElement;
import java.util.List;

public class ValidatorDefinition {
    private BreakingStrategy breakingStrategy;
    private List<ExecutableElement> constructors;
    private List<ValidationDefinition> validationDefinitions;

    public BreakingStrategy getBreakingStrategy() {
        return breakingStrategy;
    }

    public ValidatorDefinition withBreakingStrategy(BreakingStrategy breakingStrategy) {
        this.breakingStrategy = breakingStrategy;
        return this;
    }

    public List<ExecutableElement> getConstructors() {
        return constructors;
    }

    public ValidatorDefinition withConstructors(List<ExecutableElement> constructors) {
        this.constructors = constructors;
        return this;
    }

    public List<ValidationDefinition> getValidationDefinitions() {
        return validationDefinitions;
    }

    public ValidatorDefinition withValidationDefinitions(List<ValidationDefinition> validationDefinitions) {
        this.validationDefinitions = validationDefinitions;
        return this;
    }
}
