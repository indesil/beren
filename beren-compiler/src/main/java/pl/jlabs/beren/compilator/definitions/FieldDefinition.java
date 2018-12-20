package pl.jlabs.beren.compilator.definitions;

import javax.lang.model.type.TypeMirror;

public class FieldDefinition {
    private String name;
    private String[] names;
    private TypeMirror type;
    private String pattern;
    private String operation;
    private String message;

    public FieldDefinition withName(String name) {
        this.name = name;
        return this;
    }

    public FieldDefinition withNames(String[] names) {
        this.names = names;
        return this;
    }

    public FieldDefinition withType(TypeMirror type) {
        this.type = type;
        return this;
    }

    public FieldDefinition withPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public FieldDefinition withOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public FieldDefinition withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getName() {
        return name;
    }

    public String[] getNames() {
        return names;
    }

    public TypeMirror getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }

    public String getOperation() {
        return operation;
    }

    public String getMessage() {
        return message;
    }
}
