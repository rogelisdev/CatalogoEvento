package com.codeup.catalogoDeEventos.domain.exceptions;

/**
 * Excepción lanzada cuando las credenciales de autenticación son inválidas.
 */
public class AuthenticationException extends RuntimeException {
    
    public AuthenticationException(String message) {
        super(message);
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
