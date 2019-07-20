package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.DateFeaturesModel;

@Validator
public interface DateFeaturesValidator {

    @Validate(@Field(type = Object.class, operation = "future"))
    void checkFutureDateValues(DateFeaturesModel dateFeaturesModel);

    @Validate(@Field(type = Object.class, operation = "futureOrPresent"))
    void checkFutureOrPresentDateValues(DateFeaturesModel dateFeaturesModel);

    @Validate(@Field(type = Object.class, operation = "past"))
    void checkPastDateValues(DateFeaturesModel dateFeaturesModel);

    @Validate(@Field(type = Object.class, operation = "pastOrPresent"))
    void checkPastOrPresentDateValues(DateFeaturesModel dateFeaturesModel);
}
