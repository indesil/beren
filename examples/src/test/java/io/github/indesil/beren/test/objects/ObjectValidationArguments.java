package io.github.indesil.beren.test.objects;

import io.github.indesil.beren.test.features.model.ObjectFeaturesModel;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.objectFeaturesModel;

public class ObjectValidationArguments {

    public static Stream<Arguments> invalidNotNullValues() {
        return Stream.of(
                Arguments.of(new ObjectFeaturesModel().setList(new ArrayList<>())),
                Arguments.of(new ObjectFeaturesModel().setMap(new HashMap<>())),
                Arguments.of(new ObjectFeaturesModel().setIntegerWrapper(1)),
                Arguments.of(new ObjectFeaturesModel().setLongWrapper(1L)),
                Arguments.of(new ObjectFeaturesModel().setDoubleWrapper(1d)),
                Arguments.of(new ObjectFeaturesModel().setObject(new Object())),
                Arguments.of(new ObjectFeaturesModel().setSet(new HashSet<>())),
                Arguments.of(new ObjectFeaturesModel().setString("any"))
        );
    }

    public static Stream<Arguments> invalidNullValues() {
        return Stream.of(
                Arguments.of(objectFeaturesModel().setList(null)),
                Arguments.of(objectFeaturesModel().setMap(null)),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(null)),
                Arguments.of(objectFeaturesModel().setLongWrapper(null)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(null)),
                Arguments.of(objectFeaturesModel().setObject(null)),
                Arguments.of(objectFeaturesModel().setSet(null)),
                Arguments.of(objectFeaturesModel().setString(null))
        );
    }

    public static Stream<Arguments> invalidOneOfValues() {
        return Stream.of(
                Arguments.of(objectFeaturesModel().setIntegerWrapper(3), "1,10,100"),
                Arguments.of(objectFeaturesModel().setLongWrapper(30L), "1L,10L,100L"),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(200D), "1.5,10.5,100.5"),
                Arguments.of(objectFeaturesModel().setString("xyz"), "'abc', 'def', 'ghi'")
        );
    }

    public static Stream<Arguments> validOneOfValues() {
        return Stream.of(
                Arguments.of(objectFeaturesModel().setIntegerWrapper(1)),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(10)),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(100)),
                Arguments.of(objectFeaturesModel().setLongWrapper(1L)),
                Arguments.of(objectFeaturesModel().setLongWrapper(10L)),
                Arguments.of(objectFeaturesModel().setLongWrapper(100L)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(1.5d)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(10.5d)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(100.5d)),
                Arguments.of(objectFeaturesModel().setString("abc")),
                Arguments.of(objectFeaturesModel().setString("def")),
                Arguments.of(objectFeaturesModel().setString("ghi"))
        );
    }

    public static Stream<Arguments> invalidNeitherOfValues() {
        return Stream.of(
                Arguments.of(objectFeaturesModel().setIntegerWrapper(2), "2,20,200"),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(20), "2,20,200"),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(200), "2,20,200"),
                Arguments.of(objectFeaturesModel().setLongWrapper(2L), "2L,20L,200L"),
                Arguments.of(objectFeaturesModel().setLongWrapper(20L), "2L,20L,200L"),
                Arguments.of(objectFeaturesModel().setLongWrapper(200L), "2L,20L,200L"),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(2.5d), "2.5,20.5,200.5"),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(20.5d), "2.5,20.5,200.5"),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(200.5d), "2.5,20.5,200.5"),
                Arguments.of(objectFeaturesModel().setString("jkl"), "'jkl', 'mno', 'prs'"),
                Arguments.of(objectFeaturesModel().setString("mno"), "'jkl', 'mno', 'prs'"),
                Arguments.of(objectFeaturesModel().setString("prs"), "'jkl', 'mno', 'prs'")
        );
    }

    public static Stream<Arguments> validNeitherOfValues() {
        return Stream.of(
                Arguments.of(objectFeaturesModel().setIntegerWrapper(4)),
                Arguments.of(objectFeaturesModel().setLongWrapper(40L)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(400.5d)),
                Arguments.of(objectFeaturesModel().setString("xyz"))
        );
    }

    public static Stream<Arguments> nullValues() {
        return Stream.of(
                Arguments.of(objectFeaturesModel().setIntegerWrapper(null)),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(null)),
                Arguments.of(objectFeaturesModel().setIntegerWrapper(null)),
                Arguments.of(objectFeaturesModel().setLongWrapper(null)),
                Arguments.of(objectFeaturesModel().setLongWrapper(null)),
                Arguments.of(objectFeaturesModel().setLongWrapper(null)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(null)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(null)),
                Arguments.of(objectFeaturesModel().setDoubleWrapper(null)),
                Arguments.of(objectFeaturesModel().setString(null)),
                Arguments.of(objectFeaturesModel().setString(null)),
                Arguments.of(objectFeaturesModel().setString(null))
        );
    }
}
