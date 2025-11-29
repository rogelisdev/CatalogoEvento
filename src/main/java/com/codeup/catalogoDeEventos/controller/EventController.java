package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Event;
import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
import com.codeup.catalogoDeEventos.dto.EventRequest;
import com.codeup.catalogoDeEventos.service.ServiceEvent;
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
@RequestMapping("/api/event")
@AllArgsConstructor
@Tag(name = "Event", description = "API for event management")

public class EventController {

    private final ServiceEvent service;

    // ----------------------------- LIST ALL -----------------------------
    @Operation(summary = "Get all events", description = "Returns all registered events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of events retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Event.class))))
    })
    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        List<Event> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    // ----------------------------- FIND BY ID -----------------------------
    @Operation(summary = "Get event by ID", description = "Returns the event with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class),
                            examples = @ExampleObject(
                                    value = "{\"id\":1, \"name\":\"Music Festival\", \"description\":\"Annual music event\", \"date\":\"2025-12-15T20:00:00\", \"capacity\":100, \"idVenue\":1, \"price\":50000.0}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\":\"Event with ID 100 not found\"}"
                            )))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(
            @Parameter(description = "ID of the event to search", example = "1") @PathVariable long id) {

        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));
    }

    // ----------------------------- EVENT DETAILS + VENUE -----------------------------
    @Operation(summary = "Get event with venue details", description = "Returns an event with the complete information of its associated venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found with details"),
            @ApiResponse(responseCode = "404", description = "Event or associated venue not found")
    })
    @GetMapping("/{id}/details")
    public ResponseEntity<DetailsEventResponse> getDetailsById(@PathVariable long id) {
        return service.foundDetailsById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Event or associated venue", id));
    }

    // ----------------------------- CREATE EVENT -----------------------------
    @Operation(summary = "Add a new event", description = "Creates an event and registers it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class),
                            examples = @ExampleObject(
                                    value = "{\"id\":1, \"name\":\"Music Festival\", \"description\":\"Annual music event\", \"date\":\"2025-12-15T20:00:00\", \"capacity\":100, \"idVenue\":1, \"price\":50000.0}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data of the event to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EventRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"name\":\"Music Festival\", \"description\":\"Annual music event\", \"date\":\"2025-12-15T20:00:00\", \"capacity\":100, \"idVenue\":1, \"price\":50000.0}"
                            )))
            @Valid @RequestBody EventRequest request) {

        Event created = service.create(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event created successfully.");
        response.put("event", created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ----------------------------- UPDATE EVENT -----------------------------
    @Operation(summary = "Update event", description = "Updates the data of an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable long id,
            @Valid @RequestBody EventRequest request) {

        Event updated = service.updateEvent(id, request)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event updated successfully.");
        response.put("event", updated);

        return ResponseEntity.ok(response);
    }

    // ----------------------------- DELETE EVENT -----------------------------
    @Operation(summary = "Delete event", description = "Deletes an existing event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable long id) {
        if (!service.delete(id)) {
            throw new ResourceNotFoundException("Event", id);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Event with ID " + id + " deleted successfully.");
        return ResponseEntity.ok(response);
    }
}
