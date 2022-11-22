package com.enigma.rest.exception;

public class StatusDoesNotExistException extends RuntimeException {

    public StatusDoesNotExistException(String message) {
        super(message);
    }
}
