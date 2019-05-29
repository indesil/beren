package io.github.indesil.beren.test.features;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.features.model.CollectionFeaturesModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.collectionFeaturesModel;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class CollectionFeaturesValidatorTest {

    private CollectionFeaturesValidator validator = Beren.loadValidator(CollectionFeaturesValidator.class);

    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.CollectionValidationArguments#nullValuesCollections",
            "io.github.indesil.beren.test.objects.CollectionValidationArguments#emptyCollections"
    })
    @ParameterizedTest
    void shouldNotAllowEmptyCollections(CollectionFeaturesModel collectionFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkNotEmpty(collectionFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("My own default message for empty collection error - %s must not be empty!", collectionFeaturesModel.testedFieldName());
    }

    @Test
    void shouldAllowValidNotEmptyCollections() {
        // when // then
        try {
            validator.checkNotEmpty(collectionFeaturesModel(1));
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }

    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.CollectionValidationArguments#emptyCollections",
            "io.github.indesil.beren.test.objects.CollectionValidationArguments#invalidCollectionSizeArguments"
    })
    @ParameterizedTest
    void shouldNotAllowInvalidSizeValues(CollectionFeaturesModel collectionFeaturesModel) {
        // when // then
        assertThatThrownBy(() -> validator.checkSize(collectionFeaturesModel))
                .isInstanceOf(ValidationException.class)
                .hasMessage("%s size must be between 2 and 5", collectionFeaturesModel.testedFieldName());
    }

    @MethodSource(value = {
            "io.github.indesil.beren.test.objects.CollectionValidationArguments#nullValuesCollections",
            "io.github.indesil.beren.test.objects.CollectionValidationArguments#validCollectionSizeArguments"
    })
    @ParameterizedTest
    void shouldAllowValidValidSizeValues(CollectionFeaturesModel collectionFeaturesModel) {
        // when // then
        try {
            validator.checkSize(collectionFeaturesModel);
        } catch (ValidationException e) {
            fail("No validation exception should occur!");
        }
    }
}