package com.codeup.catalogoDeEventos.infrastructure.controller;

import com.codeup.catalogoDeEventos.application.dto.auth.AuthResponse;
import com.codeup.catalogoDeEventos.application.dto.auth.LoginRequest;
import com.codeup.catalogoDeEventos.application.dto.auth.RegisterRequest;
import com.codeup.catalogoDeEventos.application.service.AuthService;
import com.codeup.catalogoDeEventos.infrastructure.controller.advice.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with encrypted password and returns JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class), examples = @ExampleObject(value = "{\"token\": \"eyJhbGciOiJIUzUxMiJ9...\", \"type\": \"Bearer\", \"userId\": 1, \"username\": \"John Doe\", \"email\": \"john@example.com\", \"role\": \"ROLE_USER\", \"message\": \"Usuario registrado exitosamente\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration data", required = true, content = @Content(schema = @Schema(implementation = RegisterRequest.class), examples = @ExampleObject(value = "{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"password\": \"password123\"}"))) @Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Operation(summary = "User login", description = "Authenticates user with email and password, returns JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class), examples = @ExampleObject(value = "{\"token\": \"eyJhbGciOiJIUzUxMiJ9...\", \"type\": \"Bearer\", \"userId\": 1, \"username\": \"John Doe\", \"email\": \"john@example.com\", \"role\": \"ROLE_USER\", \"message\": \"Login exitoso\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login credentials", required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class), examples = @ExampleObject(value = "{\"email\": \"john@example.com\", \"password\": \"password123\"}"))) @Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
