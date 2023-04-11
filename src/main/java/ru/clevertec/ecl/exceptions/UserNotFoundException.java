package ru.clevertec.ecl.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(Long id) {
        super(id);
        this.message = "Requested user not found";
        this.code = 40403;
    }

    @Override
    public String getMessage() {
        return message + " (id = " + id + ")";
    }
}
