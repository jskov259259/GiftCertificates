package ru.clevertec.ecl.exceptions;

public class TagNameNotUniqueException extends ConstraintException {

    public TagNameNotUniqueException(String name) {
        super(name);
        this.message = "Tag with the such name already exists";
        this.code = 40002;
    }

    @Override
    public String getMessage() {
        return message + " (name = " + field + ")";
    }
}
