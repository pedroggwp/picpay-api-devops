package com.example.exception;

import lombok.Getter;

@Getter
public class ClientAlreadyExistsException extends RuntimeException {

    private final CustomProblemDetail problemDetail;

    public ClientAlreadyExistsException(CustomProblemDetail problemDetail) {
        this.problemDetail = problemDetail;
    }

}