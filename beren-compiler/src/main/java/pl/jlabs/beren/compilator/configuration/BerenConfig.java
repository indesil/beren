package pl.jlabs.beren.compilator.configuration;

import java.util.HashMap;
import java.util.Map;

public class BerenConfig {
    Map<String, String> operationsMappings;

    public Map<String, String> getOperationsMappings() {
        if(operationsMappings == null) {
            operationsMappings = new HashMap<>();
        }
        return operationsMappings;
    }

    public void setOperationsMappings(Map<String, String> operationsMappings) {
        this.operationsMappings = operationsMappings;
    }
}
