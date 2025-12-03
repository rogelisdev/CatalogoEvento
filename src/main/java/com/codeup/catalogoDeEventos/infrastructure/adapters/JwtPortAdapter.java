package com.codeup.catalogoDeEventos.infrastructure.adapters;

import com.codeup.catalogoDeEventos.domain.models.User;
import com.codeup.catalogoDeEventos.domain.ports.out.JwtPort;
import com.codeup.catalogoDeEventos.infrastructure.entities.UserEntity;
import com.codeup.catalogoDeEventos.infrastructure.security.JwtUtil;
import org.springframework.stereotype.Component;

/**
 * Adaptador para el puerto de JWT.
 * Implementa la interfaz del dominio usando la utilidad JwtUtil.
 * Cumple con el patrón Hexagonal (Ports & Adapters).
 */
@Component
public class JwtPortAdapter implements JwtPort {
    
    private final JwtUtil jwtUtil;
    
    public JwtPortAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    public String generateToken(User user) {
        // Convertir User a UserEntity (que implementa UserDetails)
        UserEntity userEntity = UserEntity.fromDomainModel(user);
        return jwtUtil.generateToken(userEntity);
    }
    
    @Override
    public String generateTokenWithUsernameAndRole(String username, String role) {
        return jwtUtil.generateToken(username, role);
    }
    
    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
    
    @Override
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }
    
    @Override
    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }
}
