package io.github.indesil.beren.test.features.model;

public class FeaturesTestModel<T extends FeaturesTestModel> {
    private String lastModifiedValueDescription;
    private String testedFieldName;

    public T setLastModifiedValueDescription(String fieldName, Object fieldValue) {
        this.lastModifiedValueDescription = "field: " + fieldName + " value: " + fieldValue;
        this.testedFieldName = fieldName;
        return (T) this;
    }

    public String getTestedFieldName() {
        return testedFieldName;
    }

    @Override
    public String toString() {
        return lastModifiedValueDescription;
    }
}
