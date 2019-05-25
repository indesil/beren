package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.NumberFeaturesModel;

@Validator
public abstract class NumberFeaturesValidator {

    @Validate(value = {
            @Field(type = Number.class, operation = "min(5)"),
            @Field(type = Number.class, operation = "max(100)")
    })
    abstract void checkNumberFeaturesMinMaxValues(NumberFeaturesModel input);

    @Validate(@Field(type = Number.class, operation = "between(5, 100)"))
    abstract void checkNumberFeaturesBetweenValues(NumberFeaturesModel input);

    @Validate(@Field(type = Number.class, operation = "negative"))
    abstract void checkNumberFeaturesNegativeValues(NumberFeaturesModel input);

    @Validate(@Field(type = Number.class, operation = "negativeOrZero"))
    abstract void checkNumberFeaturesNegativeOrZeroValues(NumberFeaturesModel input);

    @Validate(@Field(type = Number.class, operation = "positive"))
    abstract void checkNumberFeaturesPositiveValues(NumberFeaturesModel input);

    @Validate(@Field(type = Number.class, operation = "positiveOrZero"))
    abstract void checkNumberFeaturesPositiveOrZeroValues(NumberFeaturesModel input);

    @Validate(value = {
            @Field(names = {"primitiveInt", "wrapperInteger", "primitiveShort", "wrapperShort",
                    "primitiveLong", "wrapperLong", "primitiveByte", "wrapperByte", "bigInteger"},
                    operation = "digits(2, 0)"),
            @Field(names = { "primitiveFloat", "wrapperFloat", "primitiveDouble", "wrapperDouble",
                    "bigDecimal", "bigDecimalChars"},
                    operation = "digits(3, 2)"),
    })
    abstract void checkNumberFeaturesDigitsValues(NumberFeaturesModel input);

    @Validate(value = {
            @Field(type = Number.class, operation = "decimalMin('-50', true)"),
            @Field(name = "bigDecimalChars", operation = "decimalMin('-50', true)"),
            @Field(type = Number.class, operation = "decimalMax('1.2E+2', false)"),
            @Field(name = "bigDecimalChars", operation = "decimalMax('1.2E+2', false)")
    })
    abstract void checkNumberFeaturesDecimalMinMaxValues(NumberFeaturesModel input);
}
