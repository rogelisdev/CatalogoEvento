package com.codeup.catalogoDeEventos.application.service;

import com.codeup.catalogoDeEventos.application.dto.auth.AuthResponse;
import com.codeup.catalogoDeEventos.application.dto.auth.LoginRequest;
import com.codeup.catalogoDeEventos.application.dto.auth.RegisterRequest;
import com.codeup.catalogoDeEventos.domain.exceptions.AuthenticationException;
import com.codeup.catalogoDeEventos.domain.exceptions.DuplicateUserException;
import com.codeup.catalogoDeEventos.domain.models.User;
import com.codeup.catalogoDeEventos.domain.ports.in.AuthenticationUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.JwtPort;
import com.codeup.catalogoDeEventos.domain.ports.out.PasswordEncoderPort;
import com.codeup.catalogoDeEventos.domain.ports.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService implements AuthenticationUseCase {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtPort jwtPort;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (userPersistencePort.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new DuplicateUserException("El email " + request.getEmail() + " ya está registrado");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoderPort.encode(request.getPassword()));
        user.setRole("ROLE_USER"); // Por defecto, nuevo usuario

        User savedUser = userPersistencePort.save(user);

        // Generar token JWT
        String token = jwtPort.generateToken(savedUser);

        // Construir respuesta
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setMessage("Usuario registrado exitosamente");

        log.info("Usuario registrado exitosamente: {}", savedUser.getEmail());
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Buscar usuario por email
        User user = userPersistencePort.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Intento de login con email no encontrado: {}", request.getEmail());
                    return new AuthenticationException("Usuario no encontrado");
                });

        // Verificar contraseña
        if (!passwordEncoderPort.matches(request.getPassword(), user.getPassword())) {
            log.warn("Intento de login con contraseña incorrecta para: {}", request.getEmail());
            throw new AuthenticationException("Contraseña incorrecta");
        }

        // Generar token JWT
        String token = jwtPort.generateToken(user);

        // Construir respuesta
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setMessage("Login exitoso");

        log.info("Usuario autenticado exitosamente: {}", user.getEmail());
        return response;
    }
}
