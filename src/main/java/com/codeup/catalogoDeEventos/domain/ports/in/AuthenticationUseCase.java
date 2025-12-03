package com.codeup.catalogoDeEventos.domain.ports.in;

import com.codeup.catalogoDeEventos.application.dto.auth.AuthResponse;
import com.codeup.catalogoDeEventos.application.dto.auth.LoginRequest;
import com.codeup.catalogoDeEventos.application.dto.auth.RegisterRequest;

/**
 * Puerto de entrada para casos de uso de autenticación.
 * Define el contrato para operaciones de autenticación desde el exterior.
 * Cumple con principio de Inversión de Dependencias (DIP).
 */
public interface AuthenticationUseCase {
    
    /**
     * Registra un nuevo usuario
     * @param request datos del registro
     * @return respuesta con token y datos del usuario
     */
    AuthResponse register(RegisterRequest request);
    
    /**
     * Autentica un usuario
     * @param request credenciales de login
     * @return respuesta con token y datos del usuario
     */
    AuthResponse login(LoginRequest request);
}
