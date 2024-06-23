package com.example.exception;

import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends RuntimeException {

    private final CustomProblemDetail problemDetail;

    public AccountAlreadyExistsException(CustomProblemDetail problemDetail) {
        this.problemDetail = problemDetail;
    }

}
