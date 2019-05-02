package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.*;
import io.github.indesil.beren.test.objects.FeaturesTestObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.validCollectionObject;
import static io.github.indesil.beren.test.objects.FeaturesTestObjects.validComplexObject;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeaturesValidatorTest {

    private FeaturesValidator featuresValidator = Beren.loadValidator(FeaturesValidator.class);

    @Test
    void shouldAllowComplexObjectToBeNull() {
        // given

        // when // then
        featuresValidator.checkComplexObject(null);
    }

    @Test
    void shouldNotAllowEmptyCollectionField() {
        // given
        ComplexObject complexObject = new ComplexObject().setCollectionField(emptyList());

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("My own default message for empty collection error - collectionField must not be empty!");
    }

    @Test
    void shouldNotAllowCollectionObjectEmptyStringField1() {
        // given
        ComplexObject complexObject = new ComplexObject().addCollectionField(validCollectionObject().setStringField1(""));

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("stringField1 must not be empty!");
    }

    @ParameterizedTest
    @CsvSource(value = {"abc,", "de,f", ",ghi"}, delimiter = ';')
    void shouldNotAllowCollectionObjectEmptyStringField2ToBeOneOfInvalidValues(String invalidStringField2Value) {
        // given
        ComplexObject complexObject = validComplexObject()
                .setComplexObjectField(null)
                .addCollectionField(validCollectionObject()
                        .setStringField1(randomAlphabetic(2))
                        .setStringField2(invalidStringField2Value)
                );

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("stringField2 must be neither of ('abc,', 'de,f', ',ghi')");
    }

    @Test
    void shouldNotAllowCollectionObjectInvalidIntegerField1() {
        // given
        ComplexObject complexObject = new ComplexObject().addCollectionField(new CollectionObject()
                .setStringField1(randomAlphabetic(5))
                .setIntegerField1(10)
        );

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("integerField1 must be between 0 and 5");
    }

    @Test
    void shouldNotAllowCollectionObjectInvalidIntegerField2() {
        // given
        ComplexObject complexObject = new ComplexObject()
                .addCollectionField(validCollectionObject().setIntegerField2(100));

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("integerField2 must be one of (5,10,15)");
    }

    @Test
    void shouldNotAllowCollectionObjectInvalidEnumObjectField() {
        // given
        ComplexObject complexObject = new ComplexObject()
                .addCollectionField(validCollectionObject().setEnumObjectField(EnumObject.FIELD3));

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Invalid enum object!");
    }

    @Test
    void shouldNotAllowCollectionObjectInvalidMapKeyValues() {
        // given
        ComplexObject complexObject = new ComplexObject()
                .addCollectionField(validCollectionObject().addMapField(0, new MapValueObject()));

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Each map key must be less than -5");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "false, false, booleanField1, true",
            "true, true, booleanField2, false"
    })
    void shouldNotAllowCollectionObjectInvalidMapValues(boolean booleanField1, boolean booleanField2,
                                                        String invalidField, boolean expectedFieldValue) {
        // given
        ComplexObject complexObject = new ComplexObject()
                .addCollectionField(validCollectionObject()
                        .addMapField(-20, new MapValueObject()
                                .setBooleanField1(booleanField1)
                                .setBooleanField2(booleanField2))
                );

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be %s", invalidField, expectedFieldValue);
    }

    @Test
    void shouldAllowInnerComplexObjectFieldToBeNull() {
        // given
        ComplexObject complexObject = FeaturesTestObjects.validComplexObject().setComplexObjectField(null);

        // when // then
        featuresValidator.checkComplexObject(complexObject);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,2.5,3,4.5,5, integerPrimitiveField",
            "2,0,3,4.5,5, doubleField",
            "2,2.5,0,4.5,5, shortField",
            "2,2.5,3,0,5, floatPrimitiveField",
            "2,2.5,3,4.5,0, longField",
    })
    void shouldNotAllowInvalidObjectWithManyIntegers(int intField, Double doubleField, short shortField,
                                                     float floatField, Long longField, String invalidFieldName) {
        // given
        ComplexObject complexObject = validComplexObject()
                .setObjectWithManyNumbersField(new ObjectWithManyNumbers()
                        .setIntegerPrimitiveField(intField)
                        .setDoubleField(doubleField)
                        .setShortField(shortField)
                        .setFloatPrimitiveField(floatField)
                        .setLongField(longField));

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be greater than or equal to 2", invalidFieldName);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "xxx, aaa_X'YZ_bbb, aaa_X'YZ_bbb, aaa_X'YZ_bbb, stringField1",
            "aaa_X'YZ_bbb, xxx, aaa_X'YZ_bbb, aaa_X'YZ_bbb, stringField2",
            "aaa_X'YZ_bbb, aaa_X'YZ_bbb, xxx, aaa_X'YZ_bbb, stringField3",
            "aaa_X'YZ_bbb, aaa_X'YZ_bbb, aaa_X'YZ_bbb, xxx, stringField4"
    })
    void shouldNotAllowInvalidObjectWithManyStrings(String stringField1, String stringField2, String stringField3,
                                                    String stringField4, String invalidFieldName) {
        // given
        ComplexObject complexObject = validComplexObject()
                .setObjectWithManyStringsField(new ObjectWithManyStrings()
                        .setStringField1(stringField1)
                        .setStringField2(stringField2)
                        .setStringField3(stringField3)
                        .setStringField4(stringField4));

        // when // then
        assertThatThrownBy(() -> featuresValidator.checkComplexObject(complexObject))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must contains 'X'YZ'", invalidFieldName);
    }
}