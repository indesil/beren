package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.BooleanFeaturesModel;

@Validator
public interface BooleanFeaturesValidator {

    @Validate(@Field(type = Boolean.class, operation = "assertFalse"))
    void checkAssertFalse(BooleanFeaturesModel booleanFeaturesModel);

    @Validate(@Field(type = Boolean.class, operation = "assertTrue"))
    void checkAssertTrue(BooleanFeaturesModel booleanFeaturesModel);
}
