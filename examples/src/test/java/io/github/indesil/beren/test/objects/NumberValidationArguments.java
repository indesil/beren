package io.github.indesil.beren.test.objects;

import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.numberFeaturesObject;

public class NumberValidationArguments {
    // min(5)
    public static Stream<Arguments> invalidMinValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(10).setPrimitiveInt(0)),
                Arguments.of(numberFeaturesObject(10).setWrapperInteger(0)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveShort((short) 0)),
                Arguments.of(numberFeaturesObject(10).setWrapperShort((short) 0)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveLong(0L)),
                Arguments.of(numberFeaturesObject(10).setWrapperLong(0L)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveByte((byte) 0)),
                Arguments.of(numberFeaturesObject(10).setWrapperByte((byte) 0)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(4.9999f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(4.9999f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(4.99999999999)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(4.9999999999)),
                Arguments.of(numberFeaturesObject(10).setBigDecimal(new BigDecimal("4.999999999"))),
                Arguments.of(numberFeaturesObject(10).setBigInteger(new BigInteger("0")))
        );
    }

    // max(100)
    public static Stream<Arguments> invalidMaxValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(50).setPrimitiveInt(120)),
                Arguments.of(numberFeaturesObject(50).setWrapperInteger(120)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveShort((short) 120)),
                Arguments.of(numberFeaturesObject(50).setWrapperShort((short) 120)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveLong(120L)),
                Arguments.of(numberFeaturesObject(50).setWrapperLong(120L)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveByte((byte) 120)),
                Arguments.of(numberFeaturesObject(50).setWrapperByte((byte) 120)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(100.0001f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(100.0001f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(100.0000000001)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(100.0000000001)),
                Arguments.of(numberFeaturesObject(50).setBigDecimal(new BigDecimal(120))),
                Arguments.of(numberFeaturesObject(50).setBigInteger(new BigInteger("120")))
        );
    }

    // min(5)
    // max(100)
    public static Stream<Arguments> validMinMaxValidationArguments() {
        return Stream.concat(Stream.of(
                Arguments.of(numberFeaturesObject(5)),
                Arguments.of(numberFeaturesObject(50)),
                Arguments.of(numberFeaturesObject(100)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveFloat(5.0001f)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveFloat(99.9999f)),
                Arguments.of(numberFeaturesObject(50).setWrapperFloat(5.0001f)),
                Arguments.of(numberFeaturesObject(50).setWrapperFloat(99.9999f)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveDouble(5.0000001)),
                Arguments.of(numberFeaturesObject(50).setPrimitiveDouble(99.9999999)),
                Arguments.of(numberFeaturesObject(50).setWrapperDouble(5.0000001)),
                Arguments.of(numberFeaturesObject(50).setWrapperDouble(99.9999999)),
                Arguments.of(numberFeaturesObject(50).setBigDecimal(new BigDecimal("5.00000000001"))),
                Arguments.of(numberFeaturesObject(50).setBigDecimal(new BigDecimal("99.9999999999")))
        ), nullValues(50));
    }

    public static Stream<Arguments> invalidDigitsValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(30).setPrimitiveInt(100), 2, 0),
                Arguments.of(numberFeaturesObject(30).setWrapperInteger(200), 2, 0),
                Arguments.of(numberFeaturesObject(30).setPrimitiveShort((short) 255), 2, 0),
                Arguments.of(numberFeaturesObject(30).setWrapperShort((short) 255), 2, 0),
                Arguments.of(numberFeaturesObject(30).setPrimitiveLong(300L), 2, 0),
                Arguments.of(numberFeaturesObject(30).setWrapperLong(400L), 2, 0),
                Arguments.of(numberFeaturesObject(30).setPrimitiveByte((byte) 127), 2, 0),
                Arguments.of(numberFeaturesObject(30).setWrapperByte((byte) 127), 2, 0),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("25500")), 2, 0),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(12345.12f), 3, 2),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(1234.123f), 3, 2),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(12345.12f), 3, 2),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(1234.123f), 3, 2),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(12345.12), 3, 2),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(1234.123), 3, 2),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(12345.12), 3, 2),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(1234.125), 3, 2),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("12345.12")), 3, 2),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("12345.12"), 3, 2),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("1234.123"), 3, 2)
        );
    }

    public static Stream<Arguments> validDigitsValidationArguments() {
        return Stream.concat(Stream.of(
                Arguments.of(numberFeaturesObject(30)),
                Arguments.of(numberFeaturesObject(5)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(123.12f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(1.1f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(123.12f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(12.12f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(123.12)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(1.12)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(123.12)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(123.1)),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("123.12"))),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("12.1"))),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("123.12")),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("1.0"))
        ), nullValues(30));
    }

    public static Stream<Arguments> invalidPositiveValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(10).setPrimitiveInt(-10)),
                Arguments.of(numberFeaturesObject(10).setWrapperInteger(-10)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveShort((short) -10)),
                Arguments.of(numberFeaturesObject(10).setWrapperShort((short) -10)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveLong(-10L)),
                Arguments.of(numberFeaturesObject(10).setWrapperLong(-10L)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveByte((byte) -10)),
                Arguments.of(numberFeaturesObject(10).setWrapperByte((byte) -10)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveFloat(-0.5f)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveFloat(-0.0001f)),
                Arguments.of(numberFeaturesObject(10).setWrapperFloat(-0.5f)),
                Arguments.of(numberFeaturesObject(10).setWrapperFloat(-0.0001f)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveDouble(-0.512)),
                Arguments.of(numberFeaturesObject(10).setPrimitiveDouble(-0.000001)),
                Arguments.of(numberFeaturesObject(10).setWrapperDouble(-0.0000001)),
                Arguments.of(numberFeaturesObject(10).setWrapperDouble(-0.23111)),
                Arguments.of(numberFeaturesObject(10).setBigDecimal(new BigDecimal("-0.5"))),
                Arguments.of(numberFeaturesObject(10).setBigDecimal(new BigDecimal("-0.000000001"))),
                Arguments.of(numberFeaturesObject(10).setBigInteger(new BigInteger("-10")))
        );
    }

    public static Stream<Arguments> zeroPositiveValueArguments() {
        return zeroValueArguments(10);
    }

    public static Stream<Arguments> zeroNegativeValueArguments() {
        return zeroValueArguments(-10);
    }

    private static Stream<Arguments> zeroValueArguments(long fieldsValue) {
        return Stream.of(
                Arguments.of(numberFeaturesObject(fieldsValue).setPrimitiveInt(0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setWrapperInteger(0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setPrimitiveShort((short) 0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setWrapperShort((short) 0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setPrimitiveLong(0L)),
                Arguments.of(numberFeaturesObject(fieldsValue).setWrapperLong(0L)),
                Arguments.of(numberFeaturesObject(fieldsValue).setPrimitiveByte((byte) 0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setWrapperByte((byte) 0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setPrimitiveFloat(0.0f)),
                Arguments.of(numberFeaturesObject(fieldsValue).setWrapperFloat(0.0f)),
                Arguments.of(numberFeaturesObject(fieldsValue).setPrimitiveDouble(0.0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setWrapperDouble(0.0)),
                Arguments.of(numberFeaturesObject(fieldsValue).setBigDecimal(new BigDecimal("0.0"))),
                Arguments.of(numberFeaturesObject(fieldsValue).setBigInteger(new BigInteger("0")))
        );
    }

    public static Stream<Arguments> validPositiveOrZeroValidationArguments() {
        return Stream.concat(Stream.of(
                Arguments.of(numberFeaturesObject(10)),
                Arguments.of(numberFeaturesObject(0))
        ), nullValues(5));
    }

    public static Stream<Arguments> invalidNegativeValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(-50).setPrimitiveInt(20)),
                Arguments.of(numberFeaturesObject(-50).setWrapperInteger(20)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveShort((short) 20)),
                Arguments.of(numberFeaturesObject(-50).setWrapperShort((short) 20)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveLong(20L)),
                Arguments.of(numberFeaturesObject(-50).setWrapperLong(20L)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveByte((byte) 20)),
                Arguments.of(numberFeaturesObject(-50).setWrapperByte((byte) 20)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveFloat(2.0f)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveFloat(0.001f)),
                Arguments.of(numberFeaturesObject(-50).setWrapperFloat(2.0f)),
                Arguments.of(numberFeaturesObject(-50).setWrapperFloat(0.001f)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveDouble(20.0)),
                Arguments.of(numberFeaturesObject(-50).setPrimitiveDouble(0.00001)),
                Arguments.of(numberFeaturesObject(-50).setWrapperDouble(20.0)),
                Arguments.of(numberFeaturesObject(-50).setWrapperDouble(0.00001)),
                Arguments.of(numberFeaturesObject(-50).setBigDecimal(new BigDecimal("20.0"))),
                Arguments.of(numberFeaturesObject(-50).setBigDecimal(new BigDecimal("0.000000001"))),
                Arguments.of(numberFeaturesObject(-50).setBigInteger(new BigInteger("20")))
        );
    }

    public static Stream<Arguments> validNegativeOrZeroValidationArguments() {
        return Stream.concat(Stream.of(
                Arguments.of(numberFeaturesObject(-50)),
                Arguments.of(numberFeaturesObject(0))
        ), nullValues(-50));
    }

    //decimalMin(-50, true) -> -50 inclusive
    public static Stream<Arguments> invalidDecimalMinValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(30).setPrimitiveInt(-51)),
                Arguments.of(numberFeaturesObject(30).setWrapperInteger(-51)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveShort((short) -51)),
                Arguments.of(numberFeaturesObject(30).setWrapperShort((short) -51)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveLong(-51L)),
                Arguments.of(numberFeaturesObject(30).setWrapperLong(-51L)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveByte((byte) -51)),
                Arguments.of(numberFeaturesObject(30).setWrapperByte((byte) -51)),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("-25500"))),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(-12345.12f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(-1234.123f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(-12345.12f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(-1234.123f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(-12345.12)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(-1234.123)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(-12345.12)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(-1234.125)),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("-12345.12"))),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("-1234"))),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("-12345.12")),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("-1234.123"))
        );
    }

    public static Stream<Arguments> validDecimalMinMaxValidationArguments() {
        return Stream.concat(Stream.of(
                Arguments.of(numberFeaturesObject(-50)),
                Arguments.of(numberFeaturesObject(0)),
                Arguments.of(numberFeaturesObject(119)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(119.9999f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(119.9999f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(119.9999999)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(119.9999999)),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("119.999999999999999"))),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("119.999999999999999"))
        ), nullValues(30));
    }

    //decimalMax('1.2E+2', false) -> 120 exclusive
    public static Stream<Arguments> invalidDecimalMaxValidationArguments() {
        return Stream.of(
                Arguments.of(numberFeaturesObject(30).setPrimitiveInt(120)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveInt(121)),
                Arguments.of(numberFeaturesObject(30).setWrapperInteger(120)),
                Arguments.of(numberFeaturesObject(30).setWrapperInteger(121)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveShort((short) 120)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveShort((short) 121)),
                Arguments.of(numberFeaturesObject(30).setWrapperShort((short) 120)),
                Arguments.of(numberFeaturesObject(30).setWrapperShort((short) 121)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveLong(120L)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveLong(12111L)),
                Arguments.of(numberFeaturesObject(30).setWrapperLong(120L)),
                Arguments.of(numberFeaturesObject(30).setWrapperLong(12111L)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveByte((byte) 120)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveByte((byte) 121)),
                Arguments.of(numberFeaturesObject(30).setWrapperByte((byte) 120)),
                Arguments.of(numberFeaturesObject(30).setWrapperByte((byte) 121)),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("120"))),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("25500"))),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(120.0001f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveFloat(1234.123f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(120.0001f)),
                Arguments.of(numberFeaturesObject(30).setWrapperFloat(12345.12f)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(120.0000001)),
                Arguments.of(numberFeaturesObject(30).setPrimitiveDouble(12345.12)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(120.0000001)),
                Arguments.of(numberFeaturesObject(30).setWrapperDouble(1234.125)),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("120.0000000000001"))),
                Arguments.of(numberFeaturesObject(30).setBigDecimal(new BigDecimal("12345.12"))),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("120"))),
                Arguments.of(numberFeaturesObject(30).setBigInteger(new BigInteger("121"))),
                Arguments.of(numberFeaturesObject(30).setBigDecimalChars("1234.123"))
        );
    }

    private static Stream<Arguments> nullValues(long fieldsValues) {
        return Stream.of(
                Arguments.of(numberFeaturesObject(fieldsValues).setWrapperFloat(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setWrapperDouble(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setWrapperByte(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setWrapperInteger(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setWrapperLong(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setWrapperShort(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setBigInteger(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setBigDecimalChars(null)),
                Arguments.of(numberFeaturesObject(fieldsValues).setBigDecimal(null))
        );
    }
}