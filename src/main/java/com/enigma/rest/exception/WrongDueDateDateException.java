package com.enigma.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongDueDateDateException extends Exception {
    public WrongDueDateDateException(String message) {
        super(message);
    }

}
