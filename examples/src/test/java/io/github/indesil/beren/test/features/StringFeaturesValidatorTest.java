package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.StringFeaturesModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.fail;

class StringFeaturesValidatorTest {

    private StringFeaturesValidator validator = Beren.loadValidator(StringFeaturesValidator.class);

    @ParameterizedTest
    @MethodSource("io.github.indesil.beren.test.objects.StringValidationArguments#invalidStringValues")
    void shouldNotAllowInvalidStringsValues(StringFeaturesModel stringFeaturesModel, String expectedErrorMessage) {
        // when // then
        Assertions.assertThatThrownBy(() -> validator.checkStringFeatures(stringFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage(expectedErrorMessage);
    }

    @ParameterizedTest
    @MethodSource("io.github.indesil.beren.test.objects.StringValidationArguments#validStringValues")
    void shouldAllowInvalidStringsValues(StringFeaturesModel stringFeaturesModel) {
        // when // then
        try {
            validator.checkStringFeatures(stringFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @ParameterizedTest
    @MethodSource("io.github.indesil.beren.test.objects.StringValidationArguments#invalidPatternValues")
    void shouldNotAllowInvalidPatternValues(StringFeaturesModel stringFeaturesModel, String expectedErrorMessage) {
        // when // then
        Assertions.assertThatThrownBy(() -> validator.checkPatternsFeatures(stringFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage(expectedErrorMessage);
    }

    @ParameterizedTest
    @MethodSource("io.github.indesil.beren.test.objects.StringValidationArguments#validPatternValues")
    void shouldAllowValidPatternValues(StringFeaturesModel stringFeaturesModel) {
        // when // then
        try {
            validator.checkPatternsFeatures(stringFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }
}