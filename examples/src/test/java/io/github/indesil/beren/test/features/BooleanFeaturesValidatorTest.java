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
                .hasMessage("%s must be false", booleanFeaturesModel.getTestedFieldName());
    }

    @Test
    void shouldAllowValidAssertFalseValues() {
        // when // then
        try {
            validator.checkAssertFalse(booleanFeaturesModel(false));
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.BooleanValidationArguments#invalidAssertTrueValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidAssertTrueValues(BooleanFeaturesModel booleanFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkAssertTrue(booleanFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be true", booleanFeaturesModel.getTestedFieldName());
    }

    @Test
    void shouldAllowValidAssertTrueValues() {
        // when // then
        try {
            validator.checkAssertTrue(booleanFeaturesModel(true));
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }
}