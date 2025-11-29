package com.codeup.catalogoDeEventos.advice;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s with ID %d not found", resourceName, id));
    }
}
