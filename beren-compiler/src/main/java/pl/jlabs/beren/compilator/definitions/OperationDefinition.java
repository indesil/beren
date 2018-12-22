package pl.jlabs.beren.compilator.definitions;

import java.util.ArrayList;
import java.util.List;

public class OperationDefinition {

    private String nameRef;
    private List<String> argsValues = new ArrayList<>();

    public String getNameRef() {
        return nameRef;
    }

    public OperationDefinition withNameRef(String nameRef) {
        this.nameRef = nameRef;
        return this;
    }

    public List<String> getArgsValues() {
        return argsValues;
    }

    public OperationDefinition withArgsValues(List<String> argsValues) {
        this.argsValues = argsValues;
        return this;
    }

    @Override
    public String toString() {
        return "OperationDefinition{" +
                "nameRef='" + nameRef + '\'' +
                ", argsValues=" + argsValues +
                '}';
    }
}
