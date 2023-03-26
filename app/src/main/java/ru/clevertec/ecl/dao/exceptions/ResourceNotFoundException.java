package ru.clevertec.ecl.dao.exceptions;

public abstract class ResourceNotFoundException extends RuntimeException {

    protected Long id;
    protected Integer code;
    protected String message;

    public ResourceNotFoundException(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

}
