package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.StringFeaturesModel;

@Validator
public interface StringFeaturesValidator {

    @Validate(value = {
            @Field(name = "notBlankField", operation = "notBlank"),
            @Field(name = "startsWithField", operation = "startsWith('prefix')"),
            @Field(name = "endsWithField", operation = "endsWith('suffix')"),
            @Field(name = "containsField", operation = "contains('text')"),
            @Field(name = "emailField", operation = "email(null,0)"),
            @Field(name = "patternField", operation = "pattern('\\\\d{2}\\\\.\\\\d{2}\\\\.\\\\d{4}', 0)")
    })
    void checkStringFeatures(StringFeaturesModel stringFeaturesModel);


    @Validate(value = {
            //CASE_INSENSITIVE = 0x02;
            @Field(name = "emailField", operation = "email('\\\\w{4}@\\\\w{4}\\\\.\\\\w{2}', 2)"),
            @Field(name = "patternField", operation = "pattern('start.*end', 2)")
    })
    void checkPatternsFeatures(StringFeaturesModel stringFeaturesModel);
}
