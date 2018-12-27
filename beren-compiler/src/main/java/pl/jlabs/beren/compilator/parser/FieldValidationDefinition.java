package pl.jlabs.beren.compilator.parser;

import com.squareup.javapoet.ClassName;

import java.util.Map;

//neitherOf(a):
//    operationCall: pl.jlabs.beren.operations.Operations.neitherOf(this, a)
//    defaultMessage: "%{paramName} must be neither of %{a}"
//  greaterThan(var):
//    operationCall: pl.jlabs.beren.operations.Operations.greaterThan(this, var)
//    defaultMessage: "%{paramName} must be bigger than %{var}"
//  between(a,b):
//    operationCall: pl.jlabs.beren.operations.Operations.between(this, a, b)
//    defaultMessage: "%{paramName} must be between %{a} and %{b}"
//  notNull:
//    operationCall: java.util.Objects.nonNull(this)
//    defaultMessage: "%{paramName} must not be null!"
//  notEmpty:
//    operationCall: org.apache.commons.lang3.StringUtils.isNotEmpty(this)
//    defaultMessage: "%{paramName} must not be empty"
//
//    @Validate({
//    @Field(type = String.class, operation = "notEmpty"),
//    @Field(type = Double.class, operation = "greaterThan(0)"),
//    //jezeli to names bylo by np type to wtedy musimy przyjac array[] nie wiadomo czy w przypadku typow tez?
//    // a moze i to i to?
//    //@Field(names = {"paymentForm", "paid"}, operation = "myCustomInlineValidation", message = "Cash was not paid! Please check variable ${path}"),
//    @Field(name = "paymentForm", operation = "myCustomInlineValidation", message = "myCustomInlineValidation!"),
//    @Field(name = "customer", operation = "validateCustomer")
//  })


public class FieldValidationDefinition {
    private String errorMessageTemplate;
    private String methodName;
    private ClassName operationClass;
    private Map<String, String> params;
    private OperationType operationType = OperationType.STATIC_METHOD;

    public ClassName getOperationClass() {
        return operationClass;
    }

    public FieldValidationDefinition withOperationClass(ClassName operationClass) {
        this.operationClass = operationClass;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public FieldValidationDefinition withMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public FieldValidationDefinition withParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public FieldValidationDefinition.OperationType getOperationType() {
        return operationType;
    }

    public FieldValidationDefinition withOperationType(FieldValidationDefinition.OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public String getErrorMessageTemplate() {
        return errorMessageTemplate;
    }

    public FieldValidationDefinition withErrorMessageTemplate(String errorMessageTemplate) {
        this.errorMessageTemplate = errorMessageTemplate;
        return this;
    }

    public enum OperationType {
        INTERNAL_VALIDATOR_METHOD, CLASS_METHOD, STATIC_METHOD;
    }
}
