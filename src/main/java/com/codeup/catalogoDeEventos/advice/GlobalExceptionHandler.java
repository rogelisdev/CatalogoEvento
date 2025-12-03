package com.codeup.catalogoDeEventos.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized handler for exceptions across all controllers.
 * This ensures consistent error responses (JSON format) for clients.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles 404 Not Found errors caused by custom ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        // Using "message" key for consistency
        body.put("message", "Resource not found: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // 404
    }

    // Handles 400 Bad Request errors caused by @Valid failures (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collects all field validation errors into a map
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // 400
    }
}