package pl.jlabs.beren.compilator.definitions;

import pl.jlabs.beren.compilator.configuration.OperationConfig;

import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.List;

public class OperationDefinition {

    private String nameRef;
    private OperationConfig operationConfig;
    private ExecutableElement validationMethodReference;
    private List<String> argsValues = new ArrayList<>();

    public String getNameRef() {
        return nameRef;
    }

    public OperationDefinition withNameRef(String nameRef) {
        this.nameRef = nameRef;
        return this;
    }

    public OperationConfig getOperationConfig() {
        return operationConfig;
    }

    public OperationDefinition withOperationConfig(OperationConfig operationConfig) {
        this.operationConfig = operationConfig;
        return this;
    }

    public ExecutableElement getValidationMethodReference() {
        return validationMethodReference;
    }

    public OperationDefinition withValidationMethodReference(ExecutableElement validationMethodReference) {
        this.validationMethodReference = validationMethodReference;
        return this;
    }

    public List<String> getArgsValues() {
        return argsValues;
    }

    public OperationDefinition withArgsValues(List<String> argsValues) {
        this.argsValues = argsValues;
        return this;
    }
}
