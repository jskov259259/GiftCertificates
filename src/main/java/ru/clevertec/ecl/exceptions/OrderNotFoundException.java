package ru.clevertec.ecl.exceptions;

public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(Long id) {
        super(id);
        this.message = "Requested order not found";
        this.code = 40404;
    }

    @Override
    public String getMessage() {
        return message + " (id = " + id + ")";
    }
}