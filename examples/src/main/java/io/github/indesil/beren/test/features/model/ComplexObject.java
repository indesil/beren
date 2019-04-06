package io.github.indesil.beren.test.features.model;

import java.util.ArrayList;
import java.util.List;

public class ComplexObject {
    private List<CollectionObject> collectionField = new ArrayList<>();
    private ComplexObject complexObjectField;
    private ObjectWithManyNumbers objectWithManyNumbersField;
    private ObjectWithManyStrings objectWithManyStringsField;
    private Object objectField1;
    private Object objectField2;
    private Object objectField3;

    public List<CollectionObject> getCollectionField() {
        return collectionField;
    }

    public ComplexObject setCollectionField(List<CollectionObject> collectionField) {
        this.collectionField = collectionField;
        return this;
    }

    public ComplexObject addCollectionField(CollectionObject collectionField) {
        this.collectionField.add(collectionField);
        return this;
    }

    public ComplexObject getComplexObjectField() {
        return complexObjectField;
    }

    public ComplexObject setComplexObjectField(ComplexObject complexObjectField) {
        this.complexObjectField = complexObjectField;
        return this;
    }

    public ObjectWithManyNumbers getObjectWithManyNumbersField() {
        return objectWithManyNumbersField;
    }

    public ComplexObject setObjectWithManyNumbersField(ObjectWithManyNumbers objectWithManyNumbersField) {
        this.objectWithManyNumbersField = objectWithManyNumbersField;
        return this;
    }

    public ObjectWithManyStrings getObjectWithManyStringsField() {
        return objectWithManyStringsField;
    }

    public ComplexObject setObjectWithManyStringsField(ObjectWithManyStrings objectWithManyStringsField) {
        this.objectWithManyStringsField = objectWithManyStringsField;
        return this;
    }

    public Object getObjectField1() {
        return objectField1;
    }

    public ComplexObject setObjectField1(Object objectField1) {
        this.objectField1 = objectField1;
        return this;
    }

    public Object getObjectField2() {
        return objectField2;
    }

    public ComplexObject setObjectField2(Object objectField2) {
        this.objectField2 = objectField2;
        return this;
    }

    public Object getObjectField3() {
        return objectField3;
    }

    public ComplexObject setObjectField3(Object objectField3) {
        this.objectField3 = objectField3;
        return this;
    }
}
