package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.domain.models.User;

/**
 * Puerto de salida para la generación y validación de JWT.
 * Abstrae la implementación de seguridad JWT del dominio.
 * Cumple con principio de Inversión de Dependencias (DIP).
 */
public interface JwtPort {
    
    /**
     * Genera un token JWT para un usuario
     * @param user usuario del dominio
     * @return token JWT firmado
     */
    String generateToken(User user);
    
    /**
     * Genera un token JWT con username y rol
     * @param username nombre de usuario/email
     * @param role rol del usuario
     * @return token JWT firmado
     */
    String generateTokenWithUsernameAndRole(String username, String role);
    
    /**
     * Valida un token JWT
     * @param token token a validar
     * @return true si el token es válido
     */
    boolean validateToken(String token);
    
    /**
     * Extrae el username del token
     * @param token token JWT
     * @return username contenido en el token
     */
    String extractUsername(String token);
    
    /**
     * Extrae el rol del token
     * @param token token JWT
     * @return rol contenido en el token
     */
    String extractRole(String token);
}
