package ru.clevertec.ecl.dao.exceptions;

public class CertificateNameNotUniqueException extends ConstraintException {

    public CertificateNameNotUniqueException(String name) {
        super(name);
        this.message = "Certificate with the such name already exists";
        this.code = 40001;
    }

    @Override
    public String getMessage() {
        return message + " (name = " + field + ")";
    }
}
