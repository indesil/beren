package io.github.indesil.beren.test.features.model;

import java.util.HashMap;
import java.util.Map;

public class CollectionObject {
    private String stringField1;
    private String stringField2;
    private Integer integerField1;
    private Integer integerField2;
    private EnumObject enumObjectField;
    private Map<Integer, MapValueObject> mapField = new HashMap<>();

    public String getStringField1() {
        return stringField1;
    }

    public CollectionObject setStringField1(String stringField1) {
        this.stringField1 = stringField1;
        return this;
    }

    public String getStringField2() {
        return stringField2;
    }

    public CollectionObject setStringField2(String stringField2) {
        this.stringField2 = stringField2;
        return this;
    }

    public Integer getIntegerField1() {
        return integerField1;
    }

    public CollectionObject setIntegerField1(Integer integerField1) {
        this.integerField1 = integerField1;
        return this;
    }

    public Integer getIntegerField2() {
        return integerField2;
    }

    public CollectionObject setIntegerField2(Integer integerField2) {
        this.integerField2 = integerField2;
        return this;
    }

    public EnumObject getEnumObjectField() {
        return enumObjectField;
    }

    public CollectionObject setEnumObjectField(EnumObject enumObjectField) {
        this.enumObjectField = enumObjectField;
        return this;
    }

    public Map<Integer, MapValueObject> getMapField() {
        return mapField;
    }

    public CollectionObject setMapField(Map<Integer, MapValueObject> mapField) {
        this.mapField = mapField;
        return this;
    }

    public CollectionObject addMapField(Integer key, MapValueObject value) {
        this.mapField.put(key, value);
        return this;
    }
}
