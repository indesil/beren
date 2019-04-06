package io.github.indesil.beren.test.features.model;

public class ObjectWithManyNumbers {
    private int integerPrimitiveField;
    private Double doubleField;
    private Short shortField;
    private float floatPrimitiveField;
    private Long longField;
    private char notNumberField;

    public int getIntegerPrimitiveField() {
        return integerPrimitiveField;
    }

    public ObjectWithManyNumbers setIntegerPrimitiveField(int integerPrimitiveField) {
        this.integerPrimitiveField = integerPrimitiveField;
        return this;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public ObjectWithManyNumbers setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
        return this;
    }

    public Short getShortField() {
        return shortField;
    }

    public ObjectWithManyNumbers setShortField(Short shortField) {
        this.shortField = shortField;
        return this;
    }

    public float getFloatPrimitiveField() {
        return floatPrimitiveField;
    }

    public ObjectWithManyNumbers setFloatPrimitiveField(float floatPrimitiveField) {
        this.floatPrimitiveField = floatPrimitiveField;
        return this;
    }

    public Long getLongField() {
        return longField;
    }

    public ObjectWithManyNumbers setLongField(Long longField) {
        this.longField = longField;
        return this;
    }

    public char getNotNumberField() {
        return notNumberField;
    }

    public ObjectWithManyNumbers setNotNumberField(char notNumberField) {
        this.notNumberField = notNumberField;
        return this;
    }
}
