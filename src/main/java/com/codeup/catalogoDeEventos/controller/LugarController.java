package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ErrorResponse;
import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.LugarEntity;
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
                            array = @ArraySchema(schema = @Schema(implementation = LugarEntity.class))
                    ))
    })
    @GetMapping
    public ResponseEntity<List<LugarEntity>> listarTodo() {
        List<LugarEntity> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 2. OBTENER POR ID
    // ======================================================
    @Operation(summary = "Obtener lugar por ID", description = "Retorna el lugar con el ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lugar obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LugarEntity.class))),
            @ApiResponse(responseCode = "404",
                    description = "Lugar no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LugarEntity> obtenerPorID(
            @Parameter(description = "ID del lugar a buscar", example = "1", required = true)
            @PathVariable long id) {
        LugarEntity lugar = service.buscarPorID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar", id));
        return ResponseEntity.ok(lugar);
    }

    // ======================================================
    // 3. CREAR
    // ======================================================
    @Operation(summary = "Agregar lugar", description = "Agrega un nuevo lugar al catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lugar creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(
            @Valid @RequestBody LugarRequest request) {

        LugarEntity creado = service.crear(request);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("mensaje", "Lugar creado exitosamente");
        responseBody.put("lugar", creado);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // ======================================================
    // 4. ACTUALIZAR
    // ======================================================
    @Operation(summary = "Actualizar lugar", description = "Actualiza un lugar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @Parameter(description = "ID del lugar", example = "1", required = true)
            @PathVariable long id,
            @Valid @RequestBody LugarRequest request) {

        LugarEntity actualizado = service.actualizar(id, request)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar", id));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("mensaje", "Lugar actualizado exitosamente");
        responseBody.put("lugar", actualizado);

        return ResponseEntity.ok(responseBody);
    }

    // ======================================================
    // 5. ELIMINAR
    // ======================================================
    @Operation(summary = "Eliminar un lugar", description = "Elimina un lugar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
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
