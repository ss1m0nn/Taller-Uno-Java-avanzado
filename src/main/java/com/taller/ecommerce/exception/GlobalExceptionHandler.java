package com.taller.ecommerce.exception;

import com.taller.ecommerce.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StockInsuficienteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleStockInsuficiente(StockInsuficienteException ex) {
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntime(RuntimeException ex) {
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ErrorResponse> handleCredenciales(CredencialesInvalidasException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
    }
}
