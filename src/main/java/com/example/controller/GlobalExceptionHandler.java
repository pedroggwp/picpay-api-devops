package com.example.controller;

import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.ClientAlreadyExistsException;
import com.example.exception.CustomProblemDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<CustomProblemDetail> handleClientAlreadyExistsException(ClientAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getProblemDetail(), HttpStatus.valueOf(ex.getProblemDetail().getStatus()));
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<CustomProblemDetail> handleAccountAlreadyExistsException(AccountAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getProblemDetail(), HttpStatus.valueOf(ex.getProblemDetail().getStatus()));
    }
}