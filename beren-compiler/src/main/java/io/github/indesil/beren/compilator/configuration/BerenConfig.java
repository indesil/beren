package io.github.indesil.beren.compilator.configuration;

import java.util.HashMap;
import java.util.Map;

public class BerenConfig {
    private Map<String, OperationConfig> operationsMappings;

    public Map<String, OperationConfig> getOperationsMappings() {
        if(operationsMappings == null) {
            operationsMappings = new HashMap<>();
        }
        return operationsMappings;
    }

    public BerenConfig withOperationsMappings(Map<String, OperationConfig> operationsMappings) {
        this.operationsMappings = operationsMappings;
        return this;
    }
}
