package io.github.indesil.beren.test.features.model;

public class BooleanFeaturesModel extends FeaturesTestModel<BooleanFeaturesModel> {

    private boolean primitiveBoolean;
    private Boolean booleanWrapper;

    public boolean isPrimitiveBoolean() {
        return primitiveBoolean;
    }

    public Boolean getBooleanWrapper() {
        return booleanWrapper;
    }

    public BooleanFeaturesModel setPrimitiveBoolean(boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
        setLastModifiedValueDescription("primitiveBoolean", primitiveBoolean);
        return this;
    }

    public BooleanFeaturesModel setBooleanWrapper(Boolean booleanWrapper) {
        this.booleanWrapper = booleanWrapper;
        setLastModifiedValueDescription("booleanWrapper", booleanWrapper);
        return this;
    }
}
