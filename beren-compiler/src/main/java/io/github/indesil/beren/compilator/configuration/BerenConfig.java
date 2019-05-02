package io.github.indesil.beren.compilator.configuration;

import java.util.HashMap;
import java.util.Map;

public class BerenConfig {
    private Map<String, OperationConfig> operationsMappings = new HashMap<>();

    public Map<String, OperationConfig> getOperationsMappings() {
        return operationsMappings;
    }

    public BerenConfig withOperationsMappings(Map<String, OperationConfig> operationsMappings) {
        this.operationsMappings = operationsMappings;
        return this;
    }
}
