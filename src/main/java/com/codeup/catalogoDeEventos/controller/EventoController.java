package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Evento;
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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evento")
@AllArgsConstructor
@Tag(name = "Evento", description = "API para gestión de eventos")
public class EventoController {

    private final EventoService service;

    // ----------------------------- LISTAR TODOS -----------------------------
    @Operation(summary = "Obtener todos los eventos", description = "Retorna todos los eventos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos obtenidos exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Evento.class))))
    })
    @GetMapping
    public ResponseEntity<List<Evento>> listarTodo() {
        List<Evento> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    // ----------------------------- BUSCAR POR ID -----------------------------
    @Operation(summary = "Obtener evento por ID", description = "Retorna el evento con el ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Evento.class),
                            examples = @ExampleObject(
                                    value = "{\"id\":1, \"nombre\":\"Festival de Música\", \"descripcion\":\"Evento musical anual\", \"fecha\":\"2025-12-15T20:00:00\", \"capacidad\":100, \"idLugar\":1, \"precio\":50000.0}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"mensaje\":\"Evento con ID 100 no encontrado\"}"
                            )))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorID(
            @Parameter(description = "ID del evento a buscar", example = "1") @PathVariable long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }

    // ----------------------------- DETALLE EVENTO + LUGAR -----------------------------
    @Operation(summary = "Obtener evento con detalles del lugar", description = "Retorna un evento con la información completa de su lugar asociado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado con detalles"),
            @ApiResponse(responseCode = "404", description = "Evento o lugar asociado no encontrado")
    })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<EventoDetalleResponse> obtenerDetallePorId(@PathVariable long id) {
        return service.buscarDetallePorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evento o lugar asociado", id));
    }

    // ----------------------------- CREAR EVENTO -----------------------------
    @Operation(summary = "Agregar un nuevo evento", description = "Crea un evento y lo registra en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Evento.class),
                            examples = @ExampleObject(
                                    value = "{\"id\":1, \"nombre\":\"Festival de Música\", \"descripcion\":\"Evento musical anual\", \"fecha\":\"2025-12-15T20:00:00\", \"capacidad\":100, \"idLugar\":1, \"precio\":50000.0}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del evento a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EventoRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"nombre\":\"Festival de Música\", \"descripcion\":\"Evento musical anual\", \"fecha\":\"2025-12-15T20:00:00\", \"capacidad\":100, \"idLugar\":1, \"precio\":50000.0}"
                            )))
            @Valid @RequestBody EventoRequest request) {

        Evento nuevo = service.crear(request);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Evento creado exitosamente.");
        response.put("evento", nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ----------------------------- ACTUALIZAR EVENTO -----------------------------
    @Operation(summary = "Actualizar evento", description = "Actualiza los datos de un evento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable long id,
            @Valid @RequestBody EventoRequest request) {

        Evento actualizado = service.actualizar(id, request)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Evento actualizado exitosamente.");
        response.put("evento", actualizado);

        return ResponseEntity.ok(response);
    }

    // ----------------------------- ELIMINAR EVENTO -----------------------------
    @Operation(summary = "Eliminar evento", description = "Elimina un evento existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable long id) {
        if (!service.eliminar(id)) {
            throw new ResourceNotFoundException("Evento", id);
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Evento con ID " + id + " eliminado exitosamente.");
        return ResponseEntity.ok(response);
    }
}
