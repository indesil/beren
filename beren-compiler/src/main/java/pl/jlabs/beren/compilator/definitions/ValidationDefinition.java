package pl.jlabs.beren.compilator.definitions;

import javax.lang.model.element.ExecutableElement;
import java.util.List;

public class ValidationDefinition {
    private ExecutableElement methodToImplement;
    private String methodName;
    private ValidationParamDefinition validationParameter;
    private boolean nullable;
    private String nullableMessage;
    private List<FieldDefinition> fieldDefinitions;

    public ValidationDefinition withMethodToImplement(ExecutableElement methodToImplement) {
        this.methodToImplement = methodToImplement;
        this.methodName = methodToImplement.getSimpleName().toString();
        return this;
    }

    public ValidationDefinition withValidationParameter(ValidationParamDefinition validationParameter) {
        this.validationParameter = validationParameter;
        return this;
    }

    public ValidationDefinition withNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public ValidationDefinition withNullableMessage(String nullableMessage) {
        this.nullableMessage = nullableMessage;
        return this;
    }

    public ValidationDefinition withFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
        return this;
    }

    public ExecutableElement getMethodToImplement() {
        return methodToImplement;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getNullableMessage() {
        return nullableMessage;
    }

    public ValidationParamDefinition getValidationParameter() {
        return validationParameter;
    }

    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }
}
