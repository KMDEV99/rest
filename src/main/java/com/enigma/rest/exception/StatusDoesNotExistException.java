package com.enigma.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StatusDoesNotExistException extends RuntimeException {
    public StatusDoesNotExistException(String message) {
        super(message);
    }
}
