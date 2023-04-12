package ru.clevertec.ecl.exceptions;

public abstract class ConstraintException extends RuntimeException {

    protected String field;
    protected Integer code;
    protected String message;

    public ConstraintException(String field) {
        this.field = field;
    }

    public Integer getCode() {
        return code;
    }
}
