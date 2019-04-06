package io.github.indesil.beren.test.objects;

import io.github.indesil.beren.test.features.model.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FeaturesTestObjects {

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
                .setStringField1(randomAlphabetic(5))
                .setIntegerField1(2)
                .setIntegerField2(10)
                .setEnumObjectField(EnumObject.FIELD2)
                .addMapField(-10, validMapValueObject())
                .addMapField(-15, validMapValueObject());
    }

    public static ObjectWithManyNumbers validObjectWithManyNumbers() {
        return new ObjectWithManyNumbers()
                .setIntegerPrimitiveField(10)
                .setDoubleField(2.5)
                .setShortField((short) 10)
                .setFloatPrimitiveField(5.5f)
                .setLongField(1000L)
                .setNotNumberField('c');
    }

    public static ObjectWithManyStrings validObjectWithManyStrings() {
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
