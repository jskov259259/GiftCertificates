package ru.clevertec.ecl.rest.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.ecl.dao.exceptions.ConstraintException;
import ru.clevertec.ecl.dao.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ResponseMessageError bodyOfResponse = new ResponseMessageError(ex.getMessage(), ex.getCode());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { ConstraintException.class })
    protected ResponseEntity<Object> handleConstraintException(ConstraintException ex, WebRequest request) {
        ResponseMessageError bodyOfResponse = new ResponseMessageError(ex.getMessage(), ex.getCode());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
