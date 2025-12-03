package com.codeup.catalogoDeEventos.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un email ya existente.
 */
public class DuplicateUserException extends RuntimeException {
    
    public DuplicateUserException(String message) {
        super(message);
    }
    
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
