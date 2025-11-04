package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ErrorResponse;
import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.EventoEntity;
import com.codeup.catalogoDeEventos.dto.EventoDetalleResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/evento")
@RequiredArgsConstructor
@Tag(name = "Evento", description = "API para gestión de eventos, incluyendo paginación, filtros y detalles del lugar")
public class EventoController {

    private final EventoService eventoService;

    // ======================================================
    // 1. CREAR EVENTO
    // ======================================================
    @Operation(summary = "Crear nuevo evento",
            description = "Registra un nuevo evento con su información básica y el lugar asociado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoEntity.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"nombre\": \"Concierto de Rock\", \"categoria\": \"Música\", \"fecha\": \"2025-11-20\", \"precio\": 250.0, \"lugar\": {\"id\": 2, \"nombre\": \"Estadio Nacional\"}}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo del request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearEvento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del evento a registrar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EventoRequest.class),
                            examples = @ExampleObject(value = "{\"nombre\": \"Concierto de Rock\", \"categoria\": \"Música\", \"fecha\": \"2025-11-20\", \"precio\": 250.0, \"lugarId\": 2}")))
            @Valid @RequestBody EventoRequest request) {

        EventoEntity nuevo = eventoService.crear(request);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Evento creado exitosamente");
        response.put("evento", nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ======================================================
    // 2. LISTAR EVENTOS (CON PAGINACIÓN Y FILTROS)
    // ======================================================
    @Operation(summary = "Listar eventos con paginación y filtros opcionales",
            description = "Permite listar los eventos con soporte para paginación y filtros por ciudad, categoría o fecha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EventoEntity.class))))
    })
    @GetMapping
    public ResponseEntity<Page<EventoEntity>> listarEventos(
            @Parameter(description = "Filtrar por ciudad", example = "Bogotá") @RequestParam(required = false) String ciudad,
            @Parameter(description = "Filtrar por categoría", example = "Música") @RequestParam(required = false) String categoria,
            @Parameter(description = "Filtrar por fecha de inicio (YYYY-MM-DD)", example = "2025-11-01") @RequestParam(required = false) String fechaInicio,
            Pageable pageable) {

        Page<EventoEntity> eventos = eventoService.listarEventos(ciudad, categoria, fechaInicio, pageable);
        return ResponseEntity.ok(eventos);
    }

    // ======================================================
    // 3. OBTENER DETALLE POR ID
    // ======================================================
    @Operation(summary = "Obtener detalle completo de un evento",
            description = "Retorna la información detallada de un evento, incluyendo datos del lugar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoDetalleResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"nombre\": \"Concierto de Rock\", \"descripcion\": \"Show de rock internacional\", \"fecha\": \"2025-11-20\", \"precio\": 250.0, \"lugar\": {\"id\": 2, \"nombre\": \"Estadio Nacional\", \"ciudad\": \"Santiago\"}}"))),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventoDetalleResponse> obtenerDetalle(
            @Parameter(description = "ID del evento a consultar", example = "1", required = true)
            @PathVariable Long id) {

        Optional<EventoDetalleResponse> detalle = eventoService.buscarDetallePorId(id);
        return detalle.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }

    // ======================================================
    // 4. ACTUALIZAR EVENTO
    // ======================================================
    @Operation(summary = "Actualizar un evento existente",
            description = "Permite modificar los datos de un evento ya registrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEvento(
            @Parameter(description = "ID del evento a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody EventoRequest request) {

        Optional<EventoEntity> actualizado = eventoService.actualizar(id, request);

        if (actualizado.isEmpty()) {
            throw new ResourceNotFoundException("Evento", id);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Evento actualizado exitosamente");
        response.put("evento", actualizado.get());

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 5. ELIMINAR EVENTO
    // ======================================================
    @Operation(summary = "Eliminar evento por ID",
            description = "Elimina un evento existente por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarEvento(
            @Parameter(description = "ID del evento a eliminar", example = "1", required = true)
            @PathVariable Long id) {

        boolean eliminado = eventoService.eliminar(id);
        if (!eliminado) {
            throw new ResourceNotFoundException("Evento", id);
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Evento con ID " + id + " eliminado exitosamente.");

        return ResponseEntity.ok(response);
    }
}
