package io.github.indesil.beren.test.features;

import io.github.indesil.beren.annotations.Field;
import io.github.indesil.beren.annotations.Id;
import io.github.indesil.beren.annotations.Validate;
import io.github.indesil.beren.annotations.Validator;
import io.github.indesil.beren.test.features.model.*;

@Validator
public interface FeaturesValidator {
    @Validate(
            nullable = true,
            value = {
                    @Field(name = "collectionField", operation = "notEmpty"),
                    @Field(name = "collectionField", operation = "#forEachValue(collectionCheck)"),
                    @Field(name = "complexObjectField", operation = "checkComplexObject"),
                    @Field(name = "objectWithManyNumbersField", operation = "validateObjectsWithManyNumbers"),
                    @Field(name = "objectWithManyStringsField", operation = "validateObjectWithManyStrings"),
                    @Field(names = {"objectField1", "objectField2", "objectField3"}, operation = "notNull")

            })
    void checkComplexObject(ComplexObject complexObject);

    @Id("collectionCheck")
    @Validate(value = {
            @Field(name = "stringField1", operation = "notEmpty", message = "%{paramName} must not be empty!"),
            @Field(name = "stringField2", operation = "neitherOf(['abc,', 'de,f', ',ghi'])"),
            @Field(name = "integerField1", operation = "between(0,5)"),
            @Field(name = "integerField2", operation = "oneOf([5,10,15])"),
            @Field(name = "enumObjectField", operation = "isEnumObjectCorrect", message = "Invalid enum object!"),
            @Field(name = "mapField", operation = "#forEachKey(max(-5))", message = "Each map key must be less than -5"),
            @Field(name = "mapField", operation = "#forEachValue(validateMapValueObject)"),
    })
    void checkCollectionObject(CollectionObject collectionObject);

    default boolean isEnumObjectCorrect(EnumObject enumObject) {
        return EnumObject.FIELD1.equals(enumObject) || EnumObject.FIELD2.equals(enumObject);
    }

    @Validate(value = {
            @Field(name = "booleanField1", operation = "assertTrue"),
            @Field(name = "booleanField2", operation = "assertFalse")
    })
    void validateMapValueObject(MapValueObject mapValueObject);

    @Validate(nullableMessage = "ObjectWithManyNumbers must not be null",
            value = @Field(type = Number.class, operation = "min(2)")
    )
    void validateObjectsWithManyNumbers(ObjectWithManyNumbers objectWithManyNumbers);

    @Validate(
            nullable = true,
            value = @Field(pattern = "stringField.*", operation = "contains('X\\'YZ')"))
    void validateObjectWithManyStrings(ObjectWithManyStrings input);
}
