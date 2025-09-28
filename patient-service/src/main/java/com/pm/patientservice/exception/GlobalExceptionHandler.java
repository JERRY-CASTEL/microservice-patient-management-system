package com.pm.patientservice.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();

        if (ex.getCause() instanceof InvalidFormatException ife) {
            String fieldName = ife.getPath().get(0).getFieldName();

            if ("dateOfBirth".equals(fieldName)) {
                error.put(fieldName, "Invalid format for dateOfBirth. Use yyyy-MM-dd.");
            } else if ("registeredDate".equals(fieldName)) {
                error.put(fieldName, "Invalid format for registeredDate. Use yyyy-MM-dd'T'HH:mm:ss.");
            } else {
                error.put("error", "Invalid data format.");
            }
        } else {
            error.put("error", "Invalid request body.");
        }

        return ResponseEntity.badRequest().body(error);
    }
}
