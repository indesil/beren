package pl.jlabs.beren.compilator.configuration;

import java.util.HashMap;
import java.util.Map;

public class RawConfiguration {
    private Map<String, OperationConfig> operationsMappings;

    public Map<String, OperationConfig> getOperationsMappings() {
        if(operationsMappings == null) {
            operationsMappings = new HashMap<>();
        }
        return operationsMappings;
    }

    public void setOperationsMappings(Map<String, OperationConfig> operationsMappings) {
        this.operationsMappings = operationsMappings;
    }
}
