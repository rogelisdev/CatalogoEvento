package com.codeup.catalogoDeEventos.domain.ports.out;

/**
 * Puerto de salida para cifrado de contraseñas.
 * Abstrae la implementación de hash de contraseñas del dominio.
 * Cumple con principio de Inversión de Dependencias (DIP).
 */
public interface PasswordEncoderPort {
    
    /**
     * Cifra una contraseña
     * @param rawPassword contraseña sin cifrar
     * @return contraseña cifrada
     */
    String encode(String rawPassword);
    
    /**
     * Valida una contraseña contra su hash
     * @param rawPassword contraseña sin cifrar
     * @param encodedPassword contraseña cifrada
     * @return true si coinciden
     */
    boolean matches(String rawPassword, String encodedPassword);
}
