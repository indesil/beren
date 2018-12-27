package pl.jlabs.beren.compilator.parser;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

public class ValidationDefinition {
    private boolean nullable;
    private String nullableMessageTemplate;
    private ExecutableElement methodToImplement;
    private VariableElement validatedParam;
    private List<FieldValidationDefinition> fieldsToValidate;

    public ExecutableElement getMethodToImplement() {
        return methodToImplement;
    }

    public ValidationDefinition withMethodToImplement(ExecutableElement methodToImplement) {
        this.methodToImplement = methodToImplement;
        return this;
    }

    public String getMethodToImplementName() {
        return methodToImplement.getSimpleName().toString();
    }

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

    public VariableElement getValidatedParam() {
        return validatedParam;
    }

    public ValidationDefinition withValidatedParam(VariableElement validatedParam) {
        this.validatedParam = validatedParam;
        return this;
    }

    public String getValidatedParamName() {
        return validatedParam.getSimpleName().toString();
    }

    public List<FieldValidationDefinition> getFieldsToValidate() {
        return fieldsToValidate;
    }

    public ValidationDefinition withFieldsToValidate(List<FieldValidationDefinition> fieldsToValidate) {
        this.fieldsToValidate = fieldsToValidate;
        return this;
    }
}
