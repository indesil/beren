package pl.jlabs.beren.compilator.parser;

import java.util.List;

public class ValidationDefinition {
    private boolean nullable;
    private String nullableMessageTemplate;
    private String validatedParamName;
    private List<FieldValidationDefinition> fieldsToValidate;

    public boolean isNullable() {
        return nullable;
    }

    public ValidationDefinition withNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public String getNullableMessageTemplate() {
        return nullableMessageTemplate;
    }

    public ValidationDefinition withNullableMessageTemplate(String defaultMessageTemplate) {
        this.nullableMessageTemplate = defaultMessageTemplate;
        return this;
    }

    public String getValidatedParamName() {
        return validatedParamName;
    }

    public ValidationDefinition withValidatedParamName(String validatedParamName) {
        this.validatedParamName = validatedParamName;
        return this;
    }

    public List<FieldValidationDefinition> getFieldsToValidate() {
        return fieldsToValidate;
    }

    public ValidationDefinition withFieldsToValidate(List<FieldValidationDefinition> fieldsToValidate) {
        this.fieldsToValidate = fieldsToValidate;
        return this;
    }
}
