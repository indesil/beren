package pl.jlabs.beren.compilator.configuration;

import java.util.List;

public class OperationConfig {
    private String operationCall;
    private String defaultMessage;
    private List<String> args;

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
}
