package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.CollectionFeaturesModel;

@Validator
public abstract class CollectionFeaturesValidator {

    @Validate(@Field(pattern = ".*", operation = "notEmpty"))
    public abstract void checkNotEmpty(CollectionFeaturesModel collectionFeaturesModel);

    @Validate(@Field(pattern = ".*", operation = "size(2, 5)"))
    public abstract void checkSize(CollectionFeaturesModel collectionFeaturesModel);
}
