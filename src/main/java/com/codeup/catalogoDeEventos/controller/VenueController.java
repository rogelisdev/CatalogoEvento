package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ErrorResponse;
import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Venue;
import com.codeup.catalogoDeEventos.dto.VenueRequest;
import com.codeup.catalogoDeEventos.service.ServiceVenue;
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
@RequestMapping("/api/venue")
@AllArgsConstructor
@Tag(name = "Venue", description = "API for managing venues or locations")
public class VenueController {

    private final ServiceVenue service;

    // ======================================================
    // 1. GET ALL
    // ======================================================
    @Operation(summary = "Get all venues", description = "Returns a list of all registered venues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Venues retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Venue.class))
                    ))
    })
    @GetMapping
    public ResponseEntity<List<Venue>> getAll() {
        List<Venue> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 2. GET BY ID
    // ======================================================
    @Operation(summary = "Get venue by ID", description = "Returns the venue with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Venue retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Venue.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"name\": \"National Stadium\", \"capacity\": 50000, \"address\": \"Main Ave 123\", \"country\": \"Chile\", \"city\": \"Santiago\"}"
                            ))),
            @ApiResponse(responseCode = "404",
                    description = "Venue not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"timestamp\": \"2025-11-03T20:00:00\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Venue with ID 100 not found\", \"path\": \"/api/venue/100\"}"
                            )))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getById(
            @Parameter(description = "ID of the venue to search", example = "1", required = true)
            @PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));
    }

    // ======================================================
    // 3. CREATE
    // ======================================================
    @Operation(summary = "Create venue", description = "Adds a new venue to the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venue created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Venue.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 5, \"name\": \"Municipal Theater\", \"capacity\": 500, \"address\": \"Fake St 123\", \"country\": \"Colombia\", \"city\": \"Bogotá\"}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Venue data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VenueRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"name\": \"Municipal Theater\", \"capacity\": 500, \"address\": \"Fake St 123\", \"country\": \"Colombia\", \"city\": \"Bogotá\"}"
                            )
                    )
            )
            @Valid @RequestBody VenueRequest request) {

        Venue created = service.create(request);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Venue created successfully");
        responseBody.put("venue", created);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // ======================================================
    // 4. UPDATE
    // ======================================================
    @Operation(summary = "Update venue", description = "Updates an existing venue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Venue updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Venue.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data (e.g., empty name)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Venue not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @Parameter(description = "ID of the venue to update", example = "1", required = true)
            @PathVariable long id,
            @Valid @RequestBody VenueRequest request) {

        return service.updateVenue(id, request)
                .map(updatedVenue -> {
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("message", "Venue with ID " + id + " updated successfully.");
                    responseBody.put("venue", updatedVenue);
                    return ResponseEntity.ok(responseBody);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));
    }

    // ======================================================
    // 5. DELETE
    // ======================================================
    @Operation(summary = "Delete venue", description = "Deletes an existing venue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID of the venue to delete", example = "1", required = true)
            @PathVariable long id) {

        if (!service.delete(id)) {
            throw new ResourceNotFoundException("Venue", id);
        }

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Venue with ID " + id + " deleted successfully.");

        return ResponseEntity.ok(responseBody);
    }
}
