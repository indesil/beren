package pl.jlabs.beren.compilator.parser;

public class FieldValidationDefinition {
    private String fieldGetter;
    private String errorMessageTemplate;
    private OperationDefinition operationDefinition;

    public String getFieldGetter() {
        return fieldGetter;
    }

    public FieldValidationDefinition withFieldGetter(String fieldGetter) {
        this.fieldGetter = fieldGetter;
        return this;
    }

    public OperationDefinition getOperationDefinition() {
        return operationDefinition;
    }

    public FieldValidationDefinition withOperationDefinition(OperationDefinition operationDefinition) {
        this.operationDefinition = operationDefinition;
        return this;
    }
}
