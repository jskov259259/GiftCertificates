package ru.clevertec.ecl.exceptions;

public class CertificateNotFoundException extends ResourceNotFoundException {

    public CertificateNotFoundException(Long id) {
        super(id);
        this.message = "Requested certificate not found";
        this.code = 40401;
    }

    @Override
    public String getMessage() {
        return message + " (id = " + id + ")";
    }

}
