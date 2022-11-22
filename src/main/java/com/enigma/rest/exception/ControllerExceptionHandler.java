package com.enigma.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(WrongDueDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleWrongDateDueException(WrongDueDateException ex, WebRequest request) {

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolationError(ConstraintViolationException ex, WebRequest request) {

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getConstraintViolations().stream().
                        map(ConstraintViolation::getMessage).
                        collect(Collectors.joining(",\n")),
                request.getDescription(false)
        );
    }
}
