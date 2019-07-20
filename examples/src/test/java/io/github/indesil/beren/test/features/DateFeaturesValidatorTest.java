package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.DateFeaturesModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.dateFeaturesModel;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class DateFeaturesValidatorTest {

    private DateFeaturesValidator validator = Beren.loadValidator(DateFeaturesValidator.class);

    @ParameterizedTest
    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.DateValidationArguments#futureAndCurrentDates",
            "io.github.indesil.beren.test.objects.DateValidationArguments#invalidFutureDates"
    })
    void shouldNotAllowInvalidFutureDates(DateFeaturesModel dateFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkFutureDateValues(dateFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be a future date", dateFeaturesModel.testedFieldName());
    }

    @Test
    void shouldAllowValidFutureDates() {
        // when // then
        try {
            validator.checkFutureDateValues(dateFeaturesModel(LocalDateTime.now()
                    .plusYears(1)
                    .plusDays(1)
                    .plusMinutes(30)));
        } catch (ValidationException e) {
            fail("Input should be valid", e);
        }
    }

    @ParameterizedTest
    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.DateValidationArguments#invalidFutureDates"
    })
    void shouldNotAllowInvalidFutureOrPresentDates(DateFeaturesModel dateFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkFutureOrPresentDateValues(dateFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be a date in the present or in the future", dateFeaturesModel.testedFieldName());
    }

    @ParameterizedTest
    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.DateValidationArguments#futureAndCurrentWithSmallOffsetDates"
    })
    void shouldAllowValidFutureOrPresentDates(DateFeaturesModel dateFeaturesModel) {
        // when // then
        try {
            validator.checkFutureOrPresentDateValues(dateFeaturesModel);
        } catch (ValidationException e) {
            fail("Input should be valid", e);
        }
    }

    @ParameterizedTest
    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.DateValidationArguments#pastAndCurrentWithSmallOffsetDates",
            "io.github.indesil.beren.test.objects.DateValidationArguments#invalidPastDates"
    })
    void shouldNotAllowInvalidPastDates(DateFeaturesModel dateFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkPastDateValues(dateFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be a past date", dateFeaturesModel.testedFieldName());
    }

    @Test
    void shouldAllowValidPastDates() {
        // when // then
        try {
            validator.checkPastDateValues(dateFeaturesModel(LocalDateTime.now()
                    .minusYears(1)
                    .minusDays(1)
                    .minusMinutes(30)));
        } catch (ValidationException e) {
            fail("Input should be valid", e);
        }
    }

    @ParameterizedTest
    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.DateValidationArguments#invalidPastDates"
    })
    void shouldNotAllowInvalidPastOrPresentDates(DateFeaturesModel dateFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkPastOrPresentDateValues(dateFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be a date in the past or in the present", dateFeaturesModel.testedFieldName());
    }

    @ParameterizedTest
    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.DateValidationArguments#pastAndCurrentDates"
    })
    void shouldAllowValidPastOrPresentDates(DateFeaturesModel dateFeaturesModel) {
        // when // then
        try {
            validator.checkPastOrPresentDateValues(dateFeaturesModel);
        } catch (ValidationException e) {
            fail("Input should be valid", e);
        }
    }
}