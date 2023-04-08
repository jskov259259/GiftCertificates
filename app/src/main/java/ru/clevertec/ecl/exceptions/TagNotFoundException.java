package ru.clevertec.ecl.exceptions;

public class TagNotFoundException extends ResourceNotFoundException {

    public TagNotFoundException(Long id) {
        super(id);
        this.message = "Requested tag not found";
        this.code = 40402;
    }

    @Override
    public String getMessage() {
        return message + " (id = " + id + ")";
    }

}
