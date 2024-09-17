package com.example.handle;

import com.example.core.exception.BusinessValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(ApiErrorResponse.builder()
                .statusCode(String.valueOf(NOT_FOUND))
                .statusMsg(e.getMessage())
                .build());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpServerErrorException(HttpServerErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(ApiErrorResponse.builder()
                .statusCode(String.valueOf(e.getStatusCode().value()))
                .statusMsg(e.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnknownException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        e.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiErrorResponse.builder()
                .statusCode(String.valueOf(INTERNAL_SERVER_ERROR.value()))
                .statusMsg(e.getMessage())
                .build());
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessValidationException(HttpServerErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(ApiErrorResponse.builder()
                .statusCode(String.valueOf(e.getStatusCode().value()))
                .statusMsg(e.getMessage())
                .build());
    }
}
