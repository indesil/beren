package pl.jlabs.beren.compilator.definitions;

import javax.lang.model.element.VariableElement;
import java.util.Map;

public class ValidationParamDefinition {
    private String paramName;
    private VariableElement param;
    private Map<String, GetterDefinition> mapOfVariables;

    public String getParamName() {
        return paramName;
    }

    public ValidationParamDefinition withParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public VariableElement getParam() {
        return param;
    }

    public ValidationParamDefinition withParam(VariableElement param) {
        this.param = param;
        return this;
    }

    public Map<String, GetterDefinition> getMapOfVariables() {
        return mapOfVariables;
    }

    public ValidationParamDefinition withMapOfGetters(Map<String, GetterDefinition> mapOfGetters) {
        this.mapOfVariables = mapOfGetters;
        return this;
    }

    @Override
    public String toString() {
        return "ValidationDefinition{" +
                "paramName='" + paramName + '\'' +
                ", mapOfVariables=" + mapOfVariables +
                '}';
    }
}
