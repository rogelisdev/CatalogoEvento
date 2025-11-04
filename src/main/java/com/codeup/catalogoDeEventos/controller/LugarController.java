package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ErrorResponse;
import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Lugar;
import com.codeup.catalogoDeEventos.dto.LugarRequest;
import com.codeup.catalogoDeEventos.service.LugarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lugar")
@AllArgsConstructor
@Tag(name = "Lugar", description = "API para gestión de lugares o venues")
public class LugarController {

    private final LugarService service;

    // ======================================================
    // 1. LISTAR TODOS
    // ======================================================
    @Operation(summary = "Obtener todos los lugares", description = "Retorna todos los lugares registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de lugares obtenidos exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Lugar.class))
                    ))
    })
    @GetMapping
    public ResponseEntity<List<Lugar>> listarTodo() {
        List<Lugar> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 2. OBTENER POR ID
    // ======================================================
    @Operation(summary = "Obtener lugar por ID", description = "Retorna el lugar con el ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lugar obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Lugar.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"nombre\": \"Estadio Nacional\", \"capacidad\": 50000, \"direccion\": \"Av. Principal 123\", \"pais\": \"Chile\", \"ciudad\": \"Santiago\"}"
                            ))),
            @ApiResponse(responseCode = "404",
                    description = "Lugar no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"timestamp\": \"2025-11-03T20:00:00\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Lugar con el ID 100 no encontrado\", \"path\": \"/api/lugar/100\"}"
                            )))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Lugar> obtenerPorID(
            @Parameter(description = "ID del lugar a buscar", example = "1", required = true)
            @PathVariable long id) {
        return service.buscarPorID(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar", id));
    }

    // ======================================================
    // 3. CREAR
    // ======================================================
    @Operation(summary = "Agregar lugar", description = "Agrega un nuevo lugar o venue al catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lugar creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lugar.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 5, \"nombre\": \"Teatro Municipal\", \"capacidad\": 500, \"direccion\": \"Calle Falsa 123\", \"pais\": \"Colombia\", \"ciudad\": \"Bogotá\"}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del lugar a crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LugarRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"nombre\": \"Teatro Municipal\", \"capacidad\": 500, \"direccion\": \"Calle Falsa 123\", \"pais\": \"Colombia\", \"ciudad\": \"Bogotá\"}"
                            )
                    )
            )
            @Valid @RequestBody LugarRequest request) {

        Lugar creado = service.crear(request);

        // 🔹 Agregamos cuerpo de respuesta con mensaje y datos
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("mensaje", "Lugar creado exitosamente");
        responseBody.put("lugar", creado);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // ======================================================
    // 4. ACTUALIZAR
    // ======================================================
    @Operation(summary = "Actualizar lugar", description = "Actualiza un lugar (venue) existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lugar actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lugar.class))),
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada inválidos (ej. nombre vacío)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Lugar no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @Parameter(description = "ID del lugar para actualizar", example = "1", required = true)
            @PathVariable long id,
            @Valid @RequestBody LugarRequest request) {

        return service.actualizar(id, request)
                .map(lugarActualizado -> {
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("mensaje", "Lugar con ID " + id + " actualizado exitosamente.");
                    responseBody.put("lugar", lugarActualizado);
                    return ResponseEntity.ok(responseBody);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Lugar", id));
    }

    // ======================================================
    // 5. ELIMINAR
    // ======================================================
    @Operation(summary = "Eliminar un lugar", description = "Elimina un lugar (venue) existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lugar no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(
            @Parameter(description = "ID del lugar para eliminar", example = "1", required = true)
            @PathVariable long id) {

        if (!service.eliminar(id)) {
            throw new ResourceNotFoundException("Lugar", id);
        }

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("mensaje", "Lugar con ID " + id + " eliminado exitosamente.");

        return ResponseEntity.ok(responseBody);
    }
}
