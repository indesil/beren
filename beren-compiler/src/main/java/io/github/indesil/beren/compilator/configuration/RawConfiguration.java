package io.github.indesil.beren.compilator.configuration;

import java.util.HashMap;
import java.util.Map;

public class RawConfiguration {
    private Map<String, OperationConfig> operationsMappings;

    Map<String, OperationConfig> getOperationsMappings() {
        if(operationsMappings == null) {
            operationsMappings = new HashMap<>();
        }
        return operationsMappings;
    }

    public void setOperationsMappings(Map<String, OperationConfig> operationsMappings) {
        this.operationsMappings = operationsMappings;
    }
}
