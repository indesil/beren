package io.github.indesil.beren.test.objects;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.stringFeaturesModel;
import static io.github.indesil.beren.test.objects.ObjectUtils.*;

public class StringValidationArguments {
    public static Stream<Arguments> invalidStringValues() {
        return Stream.of(
                Arguments.of(stringFeaturesModel().setNotBlankField(null), "notBlankField must not be blank"),
                Arguments.of(stringFeaturesModel().setNotBlankField(""), "notBlankField must not be blank"),
                Arguments.of(stringFeaturesModel().setStartsWithField(prefixedRandomString("invalidPrefix")), "startsWithField must starts with 'prefix'"),
                Arguments.of(stringFeaturesModel().setEndsWithField(suffixedRandomString("invalidSuffix")), "endsWithField must ends with 'suffix'"),
                Arguments.of(stringFeaturesModel().setContainsField(wrappedRandomString("invalidTxt")), "containsField must contains 'text'"),
                Arguments.of(stringFeaturesModel().setEmailField(randomString()), "emailField must be a well-formed email address"),
                Arguments.of(stringFeaturesModel().setPatternField(randomString()), "patternField must match '\\d{2}\\.\\d{2}\\.\\d{4}'")
        );
    }

    public static Stream<Arguments> validStringValues() {
        return Stream.of(
                Arguments.of(stringFeaturesModel()),
                Arguments.of(stringFeaturesModel().setStartsWithField(null)),
                Arguments.of(stringFeaturesModel().setEndsWithField(null)),
                Arguments.of(stringFeaturesModel().setContainsField(null)),
                Arguments.of(stringFeaturesModel().setEmailField(null)),
                Arguments.of(stringFeaturesModel().setPatternField(null)),
                Arguments.of(stringFeaturesModel().setStartsWithField("prefix")),
                Arguments.of(stringFeaturesModel().setEndsWithField("suffix")),
                Arguments.of(stringFeaturesModel().setContainsField("text"))
        );
    }

    public static Stream<Arguments> invalidPatternValues() {
        return Stream.of(
                Arguments.of(stringFeaturesModel().setEmailField("invalid email pattern"), "emailField must be a well-formed email address"),
                Arguments.of(stringFeaturesModel().setEmailField("aa@bbbb.cc"), "emailField must be a well-formed email address"),
                Arguments.of(stringFeaturesModel().setEmailField("aaaa@b.cc"), "emailField must be a well-formed email address"),
                Arguments.of(stringFeaturesModel().setEmailField("aaaa@bbbb.c"), "emailField must be a well-formed email address"),
                Arguments.of(stringFeaturesModel().setPatternField("invalid text"), "patternField must match 'start.*end'"),
                Arguments.of(stringFeaturesModel().setPatternField("start text"), "patternField must match 'start.*end'"),
                Arguments.of(stringFeaturesModel().setPatternField("invalid end"), "patternField must match 'start.*end'")
        );
    }

    public static Stream<Arguments> validPatternValues() {
        return Stream.of(
                Arguments.of(stringFeaturesModel().setEmailField(null).setPatternField("start and end")),
                Arguments.of(stringFeaturesModel().setPatternField(null)),
                Arguments.of(stringFeaturesModel().setEmailField("aaaa@bbbb.cc").setPatternField("start and end")),
                Arguments.of(stringFeaturesModel().setEmailField("AAAA@BBBB.CC").setPatternField("START AND END"))
        );
    }
}
