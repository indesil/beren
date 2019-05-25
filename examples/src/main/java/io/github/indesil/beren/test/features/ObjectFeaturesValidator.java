package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.ObjectFeaturesModel;

@Validator
public abstract class ObjectFeaturesValidator {

    @Validate(@Field(type = Object.class, operation = "null"))
    abstract void checkNull(ObjectFeaturesModel objectFeaturesModel);

    @Validate(@Field(type = Object.class, operation = "notNull"))
    abstract void checkNotNull(ObjectFeaturesModel objectFeaturesModel);

    @Validate(value = {
            @Field(type = String.class, operation = "oneOf(['abc', 'def', 'ghi'])"),
            @Field(type = Integer.class, operation = "oneOf([1,10,100])"),
            @Field(type = Long.class, operation = "oneOf([1L,10L,100L])"),
            @Field(type = Double.class, operation = "oneOf([1.5,10.5,100.5])")
    })
    abstract void checkOneOf(ObjectFeaturesModel objectFeaturesModel);

    @Validate(value = {
            @Field(type = String.class, operation = "neitherOf(['jkl', 'mno', 'prs'])"),
            @Field(type = Integer.class, operation = "neitherOf([2,20,200])"),
            @Field(type = Long.class, operation = "neitherOf([2L,20L,200L])"),
            @Field(type = Double.class, operation = "neitherOf([2.5,20.5,200.5])")
    })
    abstract void checkNeitherOf(ObjectFeaturesModel objectFeaturesModel);
}
