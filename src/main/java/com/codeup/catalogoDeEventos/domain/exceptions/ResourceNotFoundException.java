package com.codeup.catalogoDeEventos.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s con ID %d no encontrado", resourceName, id));
    }
}
