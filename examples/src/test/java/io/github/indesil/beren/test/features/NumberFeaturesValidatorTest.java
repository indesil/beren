package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.NumberFeaturesModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class NumberFeaturesValidatorTest {
    private NumberFeaturesValidator validator = Beren.loadValidator(NumberFeaturesValidator.class);

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidMinValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidMinValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesMinMaxValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be greater than or equal to 5", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidMaxValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidMaxValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesMinMaxValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be less than or equal to 100", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#validMinMaxValidationArguments")
    @ParameterizedTest
    void shouldAllowValidMinMaxValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        try {
            validator.checkNumberFeaturesMinMaxValues(numberFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.NumberValidationArguments#invalidMinValidationArguments",
            "io.github.indesil.beren.test.objects.NumberValidationArguments#invalidMaxValidationArguments"
    })
    @ParameterizedTest
    void shouldNotAllowInvalidBetweenValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesBetweenValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be between 5 and 100 - test override", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#validMinMaxValidationArguments")
    @ParameterizedTest
    void shouldAllowValidBetweenValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        try {
            validator.checkNumberFeaturesBetweenValues(numberFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidDigitsValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidDigitsValue(NumberFeaturesModel numberFeaturesModel, int integer, int fraction) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesDigitsValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s numeric value out of bounds (<%d digits>.<%d digits> expected)", numberFeaturesModel.testedFieldName(), integer, fraction);
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#validDigitsValidationArguments")
    @ParameterizedTest
    void shouldAllowValidDigitsValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        try {
            validator.checkNumberFeaturesDigitsValues(numberFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.NumberValidationArguments#invalidPositiveValidationArguments",
            "io.github.indesil.beren.test.objects.NumberValidationArguments#zeroPositiveValueArguments"
    })
    @ParameterizedTest
    void shouldNotAllowInvalidPositiveValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesPositiveValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be greater than 0", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidPositiveValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidPositiveOrZeroValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesPositiveOrZeroValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be greater than or equal to 0", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#validPositiveOrZeroValidationArguments")
    @ParameterizedTest
    void shouldAllowValidPositiveOrZeroValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        try {
            validator.checkNumberFeaturesPositiveOrZeroValues(numberFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.NumberValidationArguments#invalidNegativeValidationArguments",
            "io.github.indesil.beren.test.objects.NumberValidationArguments#zeroNegativeValueArguments"
    })
    @ParameterizedTest
    void shouldNotAllowInvalidNegativeValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesNegativeValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be less than 0", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidNegativeValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidNegativeOrZeroValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesNegativeOrZeroValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be less than or equal to 0", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#validNegativeOrZeroValidationArguments")
    @ParameterizedTest
    void shouldAllowValidNegativeOrZeroValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        try {
            validator.checkNumberFeaturesNegativeOrZeroValues(numberFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidDecimalMinValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidDecimalMinValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesDecimalMinMaxValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be greater than '-50' (inclusive - true)", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#invalidDecimalMaxValidationArguments")
    @ParameterizedTest
    void shouldNotAllowInvalidDecimalMaxValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNumberFeaturesDecimalMinMaxValues(numberFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be less than '1.2E+2' (inclusive - false)", numberFeaturesModel.testedFieldName());
    }

    @MethodSource("io.github.indesil.beren.test.objects.NumberValidationArguments#validDecimalMinMaxValidationArguments")
    @ParameterizedTest
    void shouldAllowValidDecimalMinMaxValue(NumberFeaturesModel numberFeaturesModel) {
        // when // then
        try {
            validator.checkNumberFeaturesDecimalMinMaxValues(numberFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }
}