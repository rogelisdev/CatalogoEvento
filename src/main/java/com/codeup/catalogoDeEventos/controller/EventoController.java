package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Evento;
import com.codeup.catalogoDeEventos.dto.EventoReponse;
import com.codeup.catalogoDeEventos.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.ArrayList;
import java.util.List;


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
                    schema = @Schema(implementation = Evento.class),
                    examples = @ExampleObject(value = "[{\"id\":1\"nombre\":\"Conoce la casa del pollo\", \"description\":\"Gran pollo humano\", \"fecha\"2025-12-15T20:00:00\",\"capacidad\":\"100,\"idLugar\":\"1\",\"precio\":\"1000}]")
            ))
    })
    @GetMapping
    public ResponseEntity<List<Evento>> listarTodo(){
        List<Evento> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener evento por id", description = "Retorna los eventos con el id registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Eventos obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "[{\"id\":1\"nombre\":\"Conoce la casa del pollo\", \"description\":\"Gran pollo humano\", \"fecha\"2025-12-15T20:00:00\",\"capacidad\":\"100,\"idLugar\":\"1\",\"precio\":\"1000}]")
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Evento no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"timestamp\":\"2025-20-28T20:00:00\",\"status\"404,\"error\":\"Not Found\",\"message\":\"Evento con el ID 100 no encontrado\",\"path\":\"/api/evento/100\"}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorID(@Parameter(description = "Id del evento a buscar", example = "1", required = true) @PathVariable long id){
        return service.buscarID(id).map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }

    @Operation(summary = "Agregar evento", description = "Agrega los eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Eventos agregado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "[{\"id\":1\"nombre\":\"Conoce la casa del pollo\", \"description\":\"Gran pollo humano\", \"fecha\"2025-12-15T20:00:00\",\"capacidad\":\"100,\"idLugar\":\"1\",\"precio\":\"1000}]")
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Evento no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"timestamp\":\"2025-20-28T20:00:00\",\"status\"404,\"error\":\"Not Found\",\"message\":\"Evento con el ID 100 no encontrado\",\"path\":\"/api/evento/100\"}"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Evento> agregar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del evento a crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Evento.class),
                            examples = @ExampleObject(
                                    value = "{\"nombre\":\"Conoce la casa del pollo\", \"description\":\"Gran pollo humano\", \"fecha\"2025-12-15T20:00:00\",\"capacidad\":\"100,\"idLugar\":\"1\",\"precio\":\"1000}]"

                    )
            )
    )
            @Valid @RequestBody Evento evento){
        Evento crear = service.crear(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }



}


