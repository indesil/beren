package io.github.indesil.beren.test.objects;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.booleanFeaturesModel;

public class BooleanValidationArguments {

    public static Stream<Arguments> invalidAssertFalseValidationArguments() {
        return Stream.of(
                Arguments.of(booleanFeaturesModel(false).setBooleanWrapper(true)),
                Arguments.of(booleanFeaturesModel(false).setPrimitiveBoolean(true))
        );
    }

    public static Stream<Arguments> validAssertFalseValidationArguments() {
        return Stream.of(
                Arguments.of(booleanFeaturesModel(false)),
                Arguments.of(booleanFeaturesModel(false).setBooleanWrapper(null))
        );
    }

    public static Stream<Arguments> invalidAssertTrueValidationArguments() {
        return Stream.of(
                Arguments.of(booleanFeaturesModel(true).setBooleanWrapper(false)),
                Arguments.of(booleanFeaturesModel(true).setPrimitiveBoolean(false))
        );
    }

    public static Stream<Arguments> validAssertTrueValidationArguments() {
        return Stream.of(
                Arguments.of(booleanFeaturesModel(true)),
                Arguments.of(booleanFeaturesModel(true).setBooleanWrapper(null))
        );
    }
}
