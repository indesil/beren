package pl.jlabs.beren.compilator.configuration;

import java.util.List;

public class OperationConfig {
    private String operationCall;
    private String defaultMessage;
    private List<String> args;
    private OperationType operationType = OperationType.STATIC_METHOD;

    public String getOperationCall() {
        return operationCall;
    }

    public void setOperationCall(String operationCall) {
        this.operationCall = operationCall;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public OperationConfig withOperationCall(String operationCall) {
        this.operationCall = operationCall;
        return this;
    }

    public OperationConfig withDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
        return this;
    }

    public OperationConfig withArgs(List<String> args) {
        this.args = args;
        return this;
    }

    public OperationConfig withOperationType(OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public enum OperationType {
        INTERNAL_VALIDATOR_METHOD, CLASS_METHOD, STATIC_METHOD;
    }
}
