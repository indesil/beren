package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.ObjectFeaturesModel;
import io.github.indesil.beren.test.objects.FeaturesTestObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class ObjectFeaturesValidatorTest {

    private ObjectFeaturesValidator validator = Beren.loadValidator(ObjectFeaturesValidator.class);

    @MethodSource("io.github.indesil.beren.test.objects.ObjectValidationArguments#invalidNotNullValues")
    @ParameterizedTest
    void shouldNotAllowInvalidNotNullValues(ObjectFeaturesModel objectFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNull(objectFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be null", objectFeaturesModel.getTestedFieldName());
    }

    @Test
    void shouldAllowValidNullValues() {
        // when // then
        try {
            validator.checkNull(new ObjectFeaturesModel());
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.ObjectValidationArguments#invalidNullValues")
    @ParameterizedTest
    void shouldNotAllowInvalidNullValues(ObjectFeaturesModel objectFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNotNull(objectFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must not be null", objectFeaturesModel.getTestedFieldName());
    }

    @Test
    void shouldAllowValidNotNullValues() {
        // when // then
        try {
            validator.checkNotNull(FeaturesTestObjects.objectFeaturesModel());
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.ObjectValidationArguments#invalidOneOfValues")
    @ParameterizedTest
    void shouldNotAllowInvalidOneOfValues(ObjectFeaturesModel objectFeaturesModel, String range) {
        // when // then
        assertThatThrownBy(() -> validator.checkOneOf(objectFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be one of (%s)", objectFeaturesModel.getTestedFieldName(), range);
    }

    @MethodSource("io.github.indesil.beren.test.objects.ObjectValidationArguments#validOneOfValues")
    @ParameterizedTest
    void shouldAllowValidOneOfValues(ObjectFeaturesModel objectFeaturesModel) {
        // when // then
        try {
            validator.checkOneOf(objectFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource("io.github.indesil.beren.test.objects.ObjectValidationArguments#invalidNeitherOfValues")
    @ParameterizedTest
    void shouldNotAllowInvalidNeitherOfValues(ObjectFeaturesModel objectFeaturesModel, String excluded) {
        // when // then
        assertThatThrownBy(() -> validator.checkNeitherOf(objectFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s must be neither of (%s)", objectFeaturesModel.getTestedFieldName(), excluded);
    }

    @MethodSource("io.github.indesil.beren.test.objects.ObjectValidationArguments#validNeitherOfValues")
    @ParameterizedTest
    void shouldAllowValidNeitherOfValues(ObjectFeaturesModel objectFeaturesModel) {
        // when // then
        try {
            validator.checkNeitherOf(objectFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }
}