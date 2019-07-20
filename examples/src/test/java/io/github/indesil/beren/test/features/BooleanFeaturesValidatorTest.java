package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.BooleanFeaturesModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.booleanFeaturesModel;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class BooleanFeaturesValidatorTest {

    private BooleanFeaturesValidator validator = Beren.loadValidator(BooleanFeaturesValidator.class);

    @MethodSource("io.github.indesil.beren.test.objects.BooleanValidationArguments#invalidAssertFalseValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidAssertFalseValues(BooleanFeaturesModel booleanFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkAssertFalse(booleanFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be false", booleanFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.BooleanValidationArguments#validAssertFalseValidationArguments")
    @ParameterizedTest
    void shouldAllowValidAssertFalseValues(BooleanFeaturesModel booleanFeaturesModel) {
        // when // then
        try {
            validator.checkAssertFalse(booleanFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!", e);
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.BooleanValidationArguments#invalidAssertTrueValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidAssertTrueValues(BooleanFeaturesModel booleanFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkAssertTrue(booleanFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be true", booleanFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.BooleanValidationArguments#validAssertTrueValidationArguments")
    @ParameterizedTest
    void shouldAllowValidAssertTrueValues(BooleanFeaturesModel booleanFeaturesModel) {
        // when // then
        try {
            validator.checkAssertTrue(booleanFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!", e);
        }
    }
}