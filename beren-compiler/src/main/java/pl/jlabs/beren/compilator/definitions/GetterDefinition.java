package pl.jlabs.beren.compilator.definitions;

import javax.lang.model.type.TypeMirror;

public class GetterDefinition {
    private String variableGetter;
    private TypeMirror variableType;

    public String getVariableGetter() {
        return variableGetter;
    }

    public GetterDefinition withVariableGetter(String variableGetter) {
        this.variableGetter = variableGetter;
        return this;
    }

    public TypeMirror getVariableType() {
        return variableType;
    }

    public GetterDefinition withVariableType(TypeMirror variableType) {
        this.variableType = variableType;
        return this;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "getter='" + variableGetter + '\'' +
                ", type=" + variableType +
                '}';
    }
}
