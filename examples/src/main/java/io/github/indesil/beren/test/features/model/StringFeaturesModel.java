package io.github.indesil.beren.test.features.model;

public class StringFeaturesModel extends FeaturesTestModel<StringFeaturesModel> {
    private String notBlankField;
    private String startsWithField;
    private String endsWithField;
    private String containsField;
    private String emailField;
    private String patternField;

    public String getNotBlankField() {
        return notBlankField;
    }

    public String getStartsWithField() {
        return startsWithField;
    }

    public String getEndsWithField() {
        return endsWithField;
    }

    public String getContainsField() {
        return containsField;
    }

    public String getEmailField() {
        return emailField;
    }

    public String getPatternField() {
        return patternField;
    }

    public StringFeaturesModel setNotBlankField(String notBlankField) {
        this.notBlankField = notBlankField;
        setLastModifiedValueDescription("notBlankField", notBlankField);
        return this;
    }

    public StringFeaturesModel setStartsWithField(String startsWithField) {
        this.startsWithField = startsWithField;
        setLastModifiedValueDescription("startsWithField", startsWithField);
        return this;
    }

    public StringFeaturesModel setEndsWithField(String endsWithField) {
        this.endsWithField = endsWithField;
        setLastModifiedValueDescription("endsWithField", endsWithField);
        return this;
    }

    public StringFeaturesModel setContainsField(String containsField) {
        this.containsField = containsField;
        setLastModifiedValueDescription("containsField", containsField);
        return this;
    }

    public StringFeaturesModel setEmailField(String emailField) {
        this.emailField = emailField;
        setLastModifiedValueDescription("emailField", emailField);
        return this;
    }

    public StringFeaturesModel setPatternField(String patternField) {
        this.patternField = patternField;
        setLastModifiedValueDescription("patternField", patternField);
        return this;
    }
}
