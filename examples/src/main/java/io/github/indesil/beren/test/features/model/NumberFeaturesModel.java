package io.github.indesil.beren.test.features.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberFeaturesModel extends FeaturesTestModel<NumberFeaturesModel> {
    private int primitiveInt;
    private Integer wrapperInteger;
    private short primitiveShort;
    private Short wrapperShort;
    private float primitiveFloat;
    private Float wrapperFloat;
    private double primitiveDouble;
    private Double wrapperDouble;
    private long primitiveLong;
    private Long wrapperLong;
    private byte primitiveByte;
    private Byte wrapperByte;
    private BigDecimal bigDecimal;
    private BigInteger bigInteger;
    private CharSequence bigDecimalChars;

    public int getPrimitiveInt() {
        return primitiveInt;
    }

    public Integer getWrapperInteger() {
        return wrapperInteger;
    }

    public short getPrimitiveShort() {
        return primitiveShort;
    }

    public Short getWrapperShort() {
        return wrapperShort;
    }

    public float getPrimitiveFloat() {
        return primitiveFloat;
    }

    public Float getWrapperFloat() {
        return wrapperFloat;
    }

    public double getPrimitiveDouble() {
        return primitiveDouble;
    }

    public Double getWrapperDouble() {
        return wrapperDouble;
    }


    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public Long getWrapperLong() {
        return wrapperLong;
    }

    public byte getPrimitiveByte() {
        return primitiveByte;
    }

    public Byte getWrapperByte() {
        return wrapperByte;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public CharSequence getBigDecimalChars() {
        return bigDecimalChars;
    }

    public NumberFeaturesModel setPrimitiveInt(int primitiveInt) {
        this.primitiveInt = primitiveInt;
        setLastModifiedValueDescription("primitiveInt", primitiveInt);
        return this;
    }

    public NumberFeaturesModel setWrapperInteger(Integer wrapperInteger) {
        this.wrapperInteger = wrapperInteger;
        setLastModifiedValueDescription("wrapperInteger", wrapperInteger);
        return this;
    }

    public NumberFeaturesModel setPrimitiveShort(short primitiveShort) {
        this.primitiveShort = primitiveShort;
        setLastModifiedValueDescription("primitiveShort", primitiveShort);
        return this;
    }

    public NumberFeaturesModel setWrapperShort(Short wrapperShort) {
        this.wrapperShort = wrapperShort;
        setLastModifiedValueDescription("wrapperShort", wrapperShort);
        return this;
    }

    public NumberFeaturesModel setPrimitiveFloat(float primitiveFloat) {
        this.primitiveFloat = primitiveFloat;
        setLastModifiedValueDescription("primitiveFloat", primitiveFloat);
        return this;
    }

    public NumberFeaturesModel setWrapperFloat(Float wrapperFloat) {
        this.wrapperFloat = wrapperFloat;
        setLastModifiedValueDescription("wrapperFloat", wrapperFloat);
        return this;
    }

    public NumberFeaturesModel setPrimitiveDouble(double primitiveDouble) {
        this.primitiveDouble = primitiveDouble;
        setLastModifiedValueDescription("primitiveDouble", primitiveDouble);
        return this;
    }

    public NumberFeaturesModel setWrapperDouble(Double wrapperDouble) {
        this.wrapperDouble = wrapperDouble;
        setLastModifiedValueDescription("wrapperDouble", wrapperDouble);
        return this;
    }

    public NumberFeaturesModel setPrimitiveLong(long primitiveLong) {
        this.primitiveLong = primitiveLong;
        setLastModifiedValueDescription("primitiveLong", primitiveLong);
        return this;
    }

    public NumberFeaturesModel setWrapperLong(Long wrapperLong) {
        this.wrapperLong = wrapperLong;
        setLastModifiedValueDescription("wrapperLong", wrapperLong);
        return this;
    }

    public NumberFeaturesModel setPrimitiveByte(byte primitiveByte) {
        this.primitiveByte = primitiveByte;
        setLastModifiedValueDescription("primitiveByte", primitiveByte);
        return this;
    }

    public NumberFeaturesModel setWrapperByte(Byte wrapperByte) {
        this.wrapperByte = wrapperByte;
        setLastModifiedValueDescription("wrapperByte", wrapperByte);
        return this;
    }

    public NumberFeaturesModel setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        setLastModifiedValueDescription("bigDecimal", bigDecimal);
        return this;
    }

    public NumberFeaturesModel setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
        setLastModifiedValueDescription("bigInteger", bigInteger);
        return this;
    }

    public NumberFeaturesModel setBigDecimalChars(CharSequence bigDecimalChars) {
        this.bigDecimalChars = bigDecimalChars;
        setLastModifiedValueDescription("bigDecimalChars", bigDecimalChars);
        return this;
    }
}
