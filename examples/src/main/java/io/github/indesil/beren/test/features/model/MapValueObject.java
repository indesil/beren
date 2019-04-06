package io.github.indesil.beren.test.features.model;

public class MapValueObject {
    private boolean booleanField1;
    private boolean booleanField2;

    public boolean isBooleanField1() {
        return booleanField1;
    }

    public MapValueObject setBooleanField1(boolean booleanField1) {
        this.booleanField1 = booleanField1;
        return this;
    }

    public boolean isBooleanField2() {
        return booleanField2;
    }

    public MapValueObject setBooleanField2(boolean booleanField2) {
        this.booleanField2 = booleanField2;
        return this;
    }
}
