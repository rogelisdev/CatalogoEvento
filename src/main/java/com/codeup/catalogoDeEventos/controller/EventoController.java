package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Evento;

import com.codeup.catalogoDeEventos.dto.EventoRequest;
import com.codeup.catalogoDeEventos.service.EventoService;
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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/eventos")
@AllArgsConstructor
@Tag(name = "Evento", description = "API para gestion de eventos")
public class EventoController {
    private final EventoService service;

    @Operation(summary = "Obtener todos los eventos", description = "Retorna todos los eventos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Lista de eventos obtenidos exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Evento.class))
            ))
    })
    @GetMapping
    public ResponseEntity<List<Evento>> listarTodo(){
        List<Evento> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener evento por id", description = "Retorna el evento con el ID registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Evento obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            // CORRECCIÓN 1: El esquema 200 debe ser Evento.class
                            schema = @Schema(implementation = Evento.class),
                            examples = @ExampleObject(
                                    // CORRECCIÓN 2: Formato JSON válido y sin array
                                    value = "{\"id\": 1, \"nombre\": \"Conoce la casa del pollo\", \"description\": \"Gran pollo humano\", \"fecha\": \"2025-12-15T20:00:00\", \"capacidad\": 100, \"idLugar\": 1, \"precio\": 1000.0}"
                            )
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Evento no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    // Este ejemplo JSON está correcto para un 404
                                    value = "{\"timestamp\": \"2025-20-28T20:00:00\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Evento con el ID 100 no encontrado\", \"path\": \"/api/evento/100\"}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorID(@Parameter(description = "Id del evento a buscar", example = "1", required = true) @PathVariable long id){
        // La lógica de negocio es correcta: Optional.map().orElseThrow()
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }
    @Operation(summary = "Agregar evento", description = "Agrega un nuevo evento al catálogo")
    @ApiResponses(value = {
            // 1. Respuesta de éxito cambiada a 201 Created
            @ApiResponse(responseCode = "201",
                    description = "Evento creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Evento.class),
                            // 2. JSON corregido y válido, usando el EventoResponse si aplica
                            examples = @ExampleObject(value = "{\"id\": 1, \"nombre\": \"Conoce la casa del pollo\", \"descripcion\": \"Gran pollo humano\", \"fecha\": \"2025-12-15T20:00:00\", \"capacidad\": 100, \"idLugar\": 1, \"precio\": 1000.0}")
                    )),
            // 3. Añadido el 400 Bad Request
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada inválidos (ej. 'nombre' vacío)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
            // 4. Se eliminó el 404 innecesario
    })
    @PostMapping
    public ResponseEntity<Evento> agregar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del evento a crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EventoRequest.class), // Usar DTO
                            examples = @ExampleObject(
                                    // Cuerpo de request sin ID
                                    value = "{\"nombre\": \"Conoce la casa del pollo\", \"descripcion\": \"Gran pollo humano\", \"fecha\": \"2025-12-15T20:00:00\", \"capacidad\": 100, \"idLugar\": 1, \"precio\": 1000.0}"
                            )
                    )
            )
            // 5. Usar EventoRequest para recibir la solicitud
            @Valid @RequestBody EventoRequest request){

        // 6. El servicio debe aceptar EventoRequest
        Evento crear = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }

    @Operation(summary = "Actualizar evento", description = "Actualizar un evento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Evento actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Evento.class))),
            // Añadir documentación para 400
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada inválidos (ej. nombre vacío)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Evento no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
// 1. Usar EventoRequest para el DTO
    public ResponseEntity<Evento> actualizar(
            @Parameter(description = "Id del evento para actualizar", example = "1", required = true) @PathVariable long id,
            @Valid @RequestBody EventoRequest request) { // <-- Se usa EventoRequest

        // 2. Usar la lógica de Optional para manejar el 404 (debe coincidir con la firma del service)
        return service.actualizar(id, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id)); // Lanza 404 si el Optional está vacío
    }

    @Operation(summary = "Eliminar un evento", description = "Elimina un evento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento eliminado exitosamente"), // 204 No Content, no devuelve body
            @ApiResponse(responseCode = "404", description = "Evento no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable long id) {
        if (!service.eliminar(id)) {
            throw new ResourceNotFoundException("Evento", id);
        }

        // 2. Crea el cuerpo de la respuesta
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("mensaje", "Evento con ID " + id + " eliminado exitosamente.");

        // 3. Devuelve 200 OK con el cuerpo JSON
        return ResponseEntity.ok(responseBody);
    }


}


