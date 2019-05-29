package io.github.indesil.beren.test.objects;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.collectionFeaturesModel;
import static io.github.indesil.beren.test.objects.ObjectUtils.*;
import static org.apache.commons.lang3.ArrayUtils.toPrimitive;

public class CollectionValidationArguments {

    public static Stream<Arguments> nullValuesCollections() {
        return Stream.of(
                Arguments.of(collectionFeaturesModel(2).setCharSequence(null)),
                Arguments.of(collectionFeaturesModel(2).setCollection(null)),
                Arguments.of(collectionFeaturesModel(2).setMap(null)),
                Arguments.of(collectionFeaturesModel(2).setDoubleArray(null)),
                Arguments.of(collectionFeaturesModel(2).setFloatArray(null)),
                Arguments.of(collectionFeaturesModel(2).setLongArray(null)),
                Arguments.of(collectionFeaturesModel(2).setIntArray(null)),
                Arguments.of(collectionFeaturesModel(2).setShortArray(null)),
                Arguments.of(collectionFeaturesModel(2).setCharArray(null)),
                Arguments.of(collectionFeaturesModel(2).setByteArray(null)),
                Arguments.of(collectionFeaturesModel(2).setBooleanArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperDoubleArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperFloatArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperLongArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperIntArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperShortArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperCharArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperByteArray(null)),
                Arguments.of(collectionFeaturesModel(2).setWrapperBooleanArray(null)),
                Arguments.of(collectionFeaturesModel(2).setStringArray(null)),
                Arguments.of(collectionFeaturesModel(2).setObjectArray(null))
        );
    }
    public static Stream<Arguments> emptyCollections() {
        return Stream.of(
            Arguments.of(collectionFeaturesModel(2).setCharSequence("")),
            Arguments.of(collectionFeaturesModel(2).setCollection(new ArrayList<>())),
            Arguments.of(collectionFeaturesModel(2).setMap(new HashMap<>())),
            Arguments.of(collectionFeaturesModel(2).setDoubleArray(new double[0])),
            Arguments.of(collectionFeaturesModel(2).setFloatArray(new float[0])),
            Arguments.of(collectionFeaturesModel(2).setLongArray(new long[0])),
            Arguments.of(collectionFeaturesModel(2).setIntArray(new int[0])),
            Arguments.of(collectionFeaturesModel(2).setShortArray(new short[0])),
            Arguments.of(collectionFeaturesModel(2).setCharArray(new char[0])),
            Arguments.of(collectionFeaturesModel(2).setByteArray(new byte[0])),
            Arguments.of(collectionFeaturesModel(2).setBooleanArray(new boolean[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperDoubleArray(new Double[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperFloatArray(new Float[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperLongArray(new Long[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperIntArray(new Integer[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperShortArray(new Short[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperCharArray(new Character[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperByteArray(new Byte[0])),
            Arguments.of(collectionFeaturesModel(2).setWrapperBooleanArray(new Boolean[0])),
            Arguments.of(collectionFeaturesModel(2).setStringArray(new String[0])),
            Arguments.of(collectionFeaturesModel(2).setObjectArray(new Object[0]))
        );
    }

    public static Stream<Arguments> invalidCollectionSizeArguments() {
        return Stream.of(
                Arguments.of(collectionFeaturesModel(2).setCharSequence(charSequence(1))),
                Arguments.of(collectionFeaturesModel(2).setCharSequence(charSequence(7))),
                Arguments.of(collectionFeaturesModel(2).setCollection(generateCollection(1))),
                Arguments.of(collectionFeaturesModel(2).setCollection(generateCollection(7))),
                Arguments.of(collectionFeaturesModel(2).setMap(generateMap(1))),
                Arguments.of(collectionFeaturesModel(2).setMap(generateMap(7)),
                Arguments.of(collectionFeaturesModel(2).setDoubleArray(toPrimitive(randomDoubleWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setDoubleArray(toPrimitive(randomDoubleWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setFloatArray(toPrimitive(randomFloatWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setFloatArray(toPrimitive(randomFloatWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setLongArray(toPrimitive(randomLongWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setLongArray(toPrimitive(randomLongWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setIntArray(toPrimitive(randomIntWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setIntArray(toPrimitive(randomIntWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setShortArray(toPrimitive(randomShortWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setShortArray(toPrimitive(randomShortWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setCharArray(toPrimitive(randomCharWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setCharArray(toPrimitive(randomCharWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setByteArray(toPrimitive(randomByteWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setByteArray(toPrimitive(randomByteWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setBooleanArray(toPrimitive(randomBooleanWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setBooleanArray(toPrimitive(randomBooleanWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setWrapperDoubleArray(randomDoubleWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setWrapperDoubleArray(randomDoubleWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setWrapperFloatArray(randomFloatWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setWrapperFloatArray(randomFloatWrapperArray(7)))),
                Arguments.of(collectionFeaturesModel(2).setWrapperLongArray(randomLongWrapperArray(1)))),
                Arguments.of(collectionFeaturesModel(2).setWrapperLongArray(randomLongWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setWrapperIntArray(randomIntWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setWrapperIntArray(randomIntWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setWrapperShortArray(randomShortWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setWrapperShortArray(randomShortWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setWrapperCharArray(randomCharWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setWrapperCharArray(randomCharWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setWrapperByteArray(randomByteWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setWrapperByteArray(randomByteWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setWrapperBooleanArray(randomBooleanWrapperArray(1))),
                Arguments.of(collectionFeaturesModel(2).setWrapperBooleanArray(randomBooleanWrapperArray(7))),
                Arguments.of(collectionFeaturesModel(2).setStringArray(randomStringArray(1))),
                Arguments.of(collectionFeaturesModel(2).setStringArray(randomStringArray(7))),
                Arguments.of(collectionFeaturesModel(2).setObjectArray(randomObjectArray(1))),
                Arguments.of(collectionFeaturesModel(2).setObjectArray(randomObjectArray(7)))
        );
    }

    public static Stream<Arguments> validCollectionSizeArguments() {
        return Stream.of(Arguments.of(collectionFeaturesModel(3)));
    }
}
