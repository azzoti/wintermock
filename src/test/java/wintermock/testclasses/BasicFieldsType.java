package wintermock.testclasses;

import java.math.BigDecimal;

public class BasicFieldsType {
    private int primitiveIntField;
    private Integer integerField;
    private String stringField;
    private boolean primitiveBooleanField;
    private Boolean booleanField;
    private double primitiveDoubleField;
    private Double doubleField;
    private BigDecimal bigBadDecimalField;

    public int getPrimitiveIntField() {
        return primitiveIntField;
    }

    public void setPrimitiveIntField(int primitiveIntField) {
        this.primitiveIntField = primitiveIntField;
    }

    public Integer getIntegerField() {
        return integerField;
    }

    public void setIntegerField(Integer integerField) {
        this.integerField = integerField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public boolean isPrimitiveBooleanField() {
        return primitiveBooleanField;
    }

    public void setPrimitiveBooleanField(boolean primitiveBooleanField) {
        this.primitiveBooleanField = primitiveBooleanField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }

    public double getPrimitiveDoubleField() {
        return primitiveDoubleField;
    }

    public void setPrimitiveDoubleField(double primitiveDoubleField) {
        this.primitiveDoubleField = primitiveDoubleField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public BigDecimal getBigBadDecimalField() {
        return bigBadDecimalField;
    }

    public void setBigBadDecimalField(BigDecimal bigBadDecimalField) {
        this.bigBadDecimalField = bigBadDecimalField;
    }

    public BasicFieldsType(int primitiveIntField, Integer integerField, String stringField, boolean primitiveBooleanField, Boolean booleanField, double primitiveDoubleField, Double doubleField, BigDecimal bigBadDecimalField) {
        this.primitiveIntField = primitiveIntField;
        this.integerField = integerField;
        this.stringField = stringField;
        this.primitiveBooleanField = primitiveBooleanField;
        this.booleanField = booleanField;
        this.primitiveDoubleField = primitiveDoubleField;
        this.doubleField = doubleField;
        this.bigBadDecimalField = bigBadDecimalField;
    }
}
