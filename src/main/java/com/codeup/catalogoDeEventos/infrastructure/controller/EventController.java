package com.codeup.catalogoDeEventos.infrastructure.controller;

import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.application.dto.event.EventRequest;
import com.codeup.catalogoDeEventos.application.service.EventService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event", description = "API for managing events, including pagination, filters, and venue details")
public class EventController {

        private final EventService eventService;

        // ======================================================
        // 1. CREATE EVENT
        // ======================================================
        @Operation(summary = "Create a new event", description = "Registers a new event with basic information and associated venue.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Event created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class), examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Rock Concert\", \"description\": \"International rock show\", \"date\": \"2025-11-20T20:00:00\", \"price\": 250.0}"))),
                        @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<Event> createEvent(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Event data to register", required = true, content = @Content(schema = @Schema(implementation = EventRequest.class), examples = @ExampleObject(value = "{\"name\": \"Rock Concert\", \"description\": \"International rock show\", \"date\": \"2025-11-20T20:00:00\", \"price\": 250.0, \"venueId\": 2}"))) @Valid @RequestBody EventRequest request) {

                Event created = eventService.create(request);

                return new ResponseEntity<>(created, HttpStatus.CREATED);
        }

        // ======================================================
        // 2. DELETE EVENT
        // ======================================================
        @Operation(summary = "Delete event by ID", description = "Deletes an existing event by its unique identifier.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Map<String, String>> deleteEvent(
                        @PathVariable Long id) {

                boolean deleted = eventService.delete(id);

                if (!deleted) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(Map.of("error", "Event with ID " + id + " not found"));
                }

                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
}
