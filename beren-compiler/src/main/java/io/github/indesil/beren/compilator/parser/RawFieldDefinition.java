package io.github.indesil.beren.compilator.parser;

import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RawFieldDefinition {
    private String name;
    private String[] names;
    private TypeMirror type;
    private Pattern pattern;
    private String operation;
    private String message;

    public RawFieldDefinition withName(String name) {
        this.name = name;
        return this;
    }

    public RawFieldDefinition withNames(String[] names) {
        this.names = names;
        return this;
    }

    public RawFieldDefinition withType(TypeMirror type) {
        this.type = type;
        return this;
    }

    public RawFieldDefinition withPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    public RawFieldDefinition withOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public RawFieldDefinition withMessage(String message) {
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

    public Pattern getPattern() {
        return pattern;
    }

    public String getOperation() {
        return operation;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "name='" + name + '\'' +
                ", names=" + Arrays.toString(names) +
                ", type=" + type +
                ", pattern=" + pattern +
                ", operation='" + operation + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
