package com.example.core.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.List;

@Getter
public class BusinessValidationException extends RuntimeException {

    private String code;
    private List<Error> errors;

    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessValidationException(String code, String message, List<Error> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }
}
