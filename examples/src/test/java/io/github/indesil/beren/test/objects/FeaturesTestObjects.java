package io.github.indesil.beren.test.objects;

import io.github.indesil.beren.test.features.model.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static io.github.indesil.beren.test.objects.ObjectUtils.*;
import static org.apache.commons.lang3.ArrayUtils.toPrimitive;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FeaturesTestObjects {

    public static StringFeaturesModel stringFeaturesModel() {
        return new StringFeaturesModel()
            .setNotBlankField(randomString())
            .setStartsWithField(prefixedRandomString("prefix"))
            .setEndsWithField(suffixedRandomString("suffix"))
            .setContainsField(wrappedRandomString("text" ))
            .setEmailField("test@test.pl")
            .setPatternField("01.01.1970");
    }

    public static CollectionFeaturesModel collectionFeaturesModel(int collectionsSize) {
        Character[] wrapperCharArray = randomCharWrapperArray(collectionsSize);
        Short[] wrapperShortArray = randomShortWrapperArray(collectionsSize);
        Boolean[] wrapperBooleanArray = randomBooleanWrapperArray(collectionsSize);
        Byte[] wrapperByteArray = randomByteWrapperArray(collectionsSize);
        Integer[] wrapperIntArray = randomIntWrapperArray(collectionsSize);
        Long[] wrapperLongArray = randomLongWrapperArray(collectionsSize);
        Float[] wrapperFloatArray = randomFloatWrapperArray(collectionsSize);
        Double[] wrapperDoubleArray = randomDoubleWrapperArray(collectionsSize);
        return new CollectionFeaturesModel()
            .setCharSequence(charSequence(collectionsSize))
            .setCollection(generateCollection(collectionsSize))
            .setMap(generateMap(collectionsSize))
            .setDoubleArray(toPrimitive(wrapperDoubleArray))
            .setFloatArray(toPrimitive(wrapperFloatArray))
            .setLongArray(toPrimitive(wrapperLongArray))
            .setIntArray(toPrimitive(wrapperIntArray))
            .setShortArray(toPrimitive(wrapperShortArray))
            .setCharArray(toPrimitive(wrapperCharArray))
            .setByteArray(toPrimitive(wrapperByteArray))
            .setBooleanArray(toPrimitive(wrapperBooleanArray))
            .setWrapperDoubleArray(wrapperDoubleArray)
            .setWrapperFloatArray(wrapperFloatArray)
            .setWrapperLongArray(wrapperLongArray)
            .setWrapperIntArray(wrapperIntArray)
            .setWrapperShortArray(wrapperShortArray)
            .setWrapperCharArray(wrapperCharArray)
            .setWrapperByteArray(wrapperByteArray)
            .setWrapperBooleanArray(wrapperBooleanArray)
            .setStringArray(randomStringArray(collectionsSize))
            .setObjectArray(randomObjectArray(collectionsSize));
    }

    public static BooleanFeaturesModel booleanFeaturesModel(boolean valueToSet) {
        return new BooleanFeaturesModel()
                .setBooleanWrapper(valueToSet)
                .setPrimitiveBoolean(valueToSet);
    }

    public static ObjectFeaturesModel objectFeaturesModel() {
        return new ObjectFeaturesModel()
                .setList(new ArrayList<>())
                .setMap(new HashMap<>())
                .setIntegerWrapper(1)
                .setLongWrapper(10L)
                .setDoubleWrapper(100.5d)
                .setObject(new Object())
                .setSet(new HashSet<>())
                .setString("abc");
    }

    static NumberFeaturesModel numberFeaturesObject(long fieldsValue) {
        return new NumberFeaturesModel()
                .setPrimitiveInt((int) fieldsValue)
                .setWrapperInteger((int) fieldsValue)
                .setPrimitiveShort((short) fieldsValue)
                .setWrapperShort((short) fieldsValue)
                .setPrimitiveFloat((float) fieldsValue)
                .setWrapperFloat((float) fieldsValue)
                .setPrimitiveDouble(fieldsValue)
                .setWrapperDouble((double) fieldsValue)
                .setPrimitiveLong(fieldsValue)
                .setWrapperLong(fieldsValue)
                .setPrimitiveByte((byte) fieldsValue)
                .setWrapperByte((byte) fieldsValue)
                .setBigDecimal(new BigDecimal(fieldsValue))
                .setBigInteger(new BigInteger("" + fieldsValue))
                .setBigDecimalChars("" + fieldsValue)
                .setLastModifiedValueDescription("all fields value ", fieldsValue);
    }

    public static ComplexObject validComplexObject() {
        return createComplexObject().setComplexObjectField(createComplexObject());
    }

    private static ComplexObject createComplexObject() {
        return new ComplexObject()
                .addCollectionField(validCollectionObject())
                .addCollectionField(validCollectionObject())
                .setObjectWithManyNumbersField(validObjectWithManyNumbers())
                .setObjectWithManyStringsField(validObjectWithManyStrings())
                .setObjectField1(new Object())
                .setObjectField2(new Object())
                .setObjectField3(new Object());
    }

    public static CollectionObject validCollectionObject() {
        return new CollectionObject()
                .setStringField1(randomAlphabetic(WORD_SIZE))
                .setIntegerField1(2)
                .setIntegerField2(10)
                .setEnumObjectField(EnumObject.FIELD2)
                .addMapField(-10, validMapValueObject())
                .addMapField(-15, validMapValueObject());
    }

    private static ObjectWithManyNumbers validObjectWithManyNumbers() {
        return new ObjectWithManyNumbers()
                .setIntegerPrimitiveField(10)
                .setDoubleField(2.5)
                .setShortField((short) 10)
                .setFloatPrimitiveField(5.5f)
                .setLongField(1000L)
                .setNotNumberField('c');
    }

    private static ObjectWithManyStrings validObjectWithManyStrings() {
        return new ObjectWithManyStrings()
                .setStringField1(randomAlphabetic(2) + "X'YZ" + randomAlphabetic(2))
                .setStringField2(randomAlphabetic(2) + "X'YZ" + randomAlphabetic(2))
                .setStringField3(randomAlphabetic(2) + "X'YZ" + randomAlphabetic(2))
                .setStringField4(randomAlphabetic(2) + "X'YZ" + randomAlphabetic(2))
                .setNotAString((short) 2);
    }

    private static MapValueObject validMapValueObject() {
        return new MapValueObject()
                .setBooleanField1(true)
                .setBooleanField2(false);
    }
}
