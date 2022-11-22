package com.enigma.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongDueDateException extends RuntimeException {
    public WrongDueDateException(String message) {
        super(message);
    }

}
