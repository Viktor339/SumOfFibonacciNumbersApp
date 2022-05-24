package com.example.client.controller;

import com.example.client.service.exception.ExceptionResponse;
import com.example.client.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(ValidationException ex) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .build();
    }

}