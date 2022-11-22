package com.enigma.rest.controller;

import com.enigma.rest.exception.WrongDueDateDateException;
import com.enigma.rest.model.Task;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TestController {

    @ExceptionHandler(WrongDueDateDateException)
}
