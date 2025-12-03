package com.codeup.catalogoDeEventos.infrastructure.controller;

import com.codeup.catalogoDeEventos.domain.models.Venue;
import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.application.service.VenueService;
import com.codeup.catalogoDeEventos.infrastructure.controller.advice.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
@Tag(name = "Venue", description = "API for managing venues")
public class VenueController {

    private final VenueService venueService;

    // ======================================================
    // 1. GET ALL VENUES
    // ======================================================
    @Operation(summary = "Get all venues", description = "Returns all registered venues. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of venues retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token required")
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAll();
        return ResponseEntity.ok(venues);
    }

    // ======================================================
    // 2. GET VENUE BY ID
    // ======================================================
    @Operation(summary = "Get venue by ID", description = "Returns the venue with the specified ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token required"),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Venue venue = venueService.getById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found with ID " + id));
        return ResponseEntity.ok(venue);
    }

    // ======================================================
    // 3. CREATE VENUE
    // ======================================================
    @Operation(summary = "Create a new venue", description = "Adds a new venue to the catalog. Only ADMIN users can create venues.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venue created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only ADMIN users can create venues")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> createVenue(@Valid @RequestBody VenueRequest request) {
        Venue created = venueService.create(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Venue created successfully");
        response.put("venue", created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ======================================================
    // 4. UPDATE VENUE
    // ======================================================
    @Operation(summary = "Update an existing venue", description = "Updates the data of an existing venue. Only ADMIN users can update venues.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only ADMIN users can update venues")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueRequest request) {
        Venue updated = venueService.update(id, request)
                .orElseThrow(() -> new RuntimeException("Venue not found with ID " + id));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Venue updated successfully");
        response.put("venue", updated);

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 5. DELETE VENUE
    // ======================================================
    @Operation(summary = "Delete a venue by ID", description = "Deletes an existing venue by its unique identifier. Only ADMIN users can delete venues.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only ADMIN users can delete venues")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        boolean deleted = venueService.delete(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }
}
