package io.github.indesil.beren.compilator.parser;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Map;

public class FieldValidationDefinition {
    private String errorMessageTemplate;
    private String methodName;
    private ClassName operationClass;
    private Map<String, String> params;
    private GetterDefinition getterDefinition;
    private ExecutableElement getterMethod;
    private List<String> paramsOrder;
    private OperationType operationType = OperationType.STATIC_METHOD;
    private IterationType iterationType = IterationType.NONE;

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

    public GetterDefinition getGetterDefinition() {
        return getterDefinition;
    }

    public FieldValidationDefinition withGetterDefinition(GetterDefinition getterDefinition) {
        this.getterDefinition = getterDefinition;
        return this;
    }

    public List<String> getParamsOrder() {
        return paramsOrder;
    }

    public FieldValidationDefinition withParamsOrder(List<String> paramsOrder) {
        this.paramsOrder = paramsOrder;
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

    public IterationType getIterationType() {
        return iterationType;
    }

    public FieldValidationDefinition withIterationType(IterationType iterationType) {
        this.iterationType = iterationType;
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
