package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ErrorResponse;
import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.VenueEntity;
import com.codeup.catalogoDeEventos.dto.VenueRequest;
import com.codeup.catalogoDeEventos.service.VenueService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Venue Management", description = "API for managing event venues")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VenueController {

    private final VenueService venueService;

    // ======================================================
    // 1. CREATE VENUE
    // ======================================================
    @Operation(
            summary = "Create new venue",
            description = "Registers a new event venue with its complete information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Venue created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VenueEntity.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data or venue name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> createVenue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Venue data to register",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VenueRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "name": "National Stadium",
                                              "address": "K2 #10-100",
                                              "city": "Bogotá",
                                              "country": "Colombia",
                                              "capacity": 5000
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody VenueRequest request) {

        log.info("Creating new venue: {}", request.getName());

        VenueEntity newVenue = venueService.create(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Venue created successfully");
        response.put("venue", newVenue);
        response.put("timestamp", LocalDateTime.now());

        log.info("Venue created successfully with ID: {}", newVenue.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ======================================================
    // 2. LIST ALL VENUES (WITH PAGINATION)
    // ======================================================
    @Operation(
            summary = "List all venues with pagination",
            description = "Retrieve a paginated list of all venues"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VenueEntity.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> listVenues(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort field", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,

            @Parameter(description = "Sort direction", example = "ASC")
            @RequestParam(defaultValue = "ASC") String direction) {

        log.info("Listing venues - page: {}, size: {}", page, size);

        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<VenueEntity> venues = venueService.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("venues", venues.getContent());
        response.put("currentPage", venues.getNumber());
        response.put("totalItems", venues.getTotalElements());
        response.put("totalPages", venues.getTotalPages());
        response.put("pageSize", venues.getSize());
        response.put("hasNext", venues.hasNext());
        response.put("hasPrevious", venues.hasPrevious());

        log.info("Retrieved {} venues", venues.getNumberOfElements());

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 3. GET VENUE BY ID
    // ======================================================
    @Operation(
            summary = "Get venue details by ID",
            description = "Returns complete information of a specific venue"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VenueEntity.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venue not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueEntity> getVenueById(
            @Parameter(description = "Venue ID", example = "1", required = true)
            @PathVariable Long id) {

        log.info("Fetching venue with ID: {}", id);

        VenueEntity venue = venueService.findById(id)
                .orElseThrow(() -> {
                    log.error("Venue not found with ID: {}", id);
                    return new ResourceNotFoundException("Venue", id);
                });

        log.info("Venue retrieved successfully: {}", venue.getName());

        return ResponseEntity.ok(venue);
    }

    // ======================================================
    // 4. UPDATE VENUE
    // ======================================================
    @Operation(
            summary = "Update an existing venue",
            description = "Modifies all fields of an existing venue"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VenueEntity.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venue not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVenue(
            @Parameter(description = "Venue ID to update", example = "1", required = true)
            @PathVariable Long id,

            @Valid @RequestBody VenueRequest request) {

        log.info("Updating venue with ID: {}", id);

        VenueEntity updatedVenue = venueService.update(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Venue updated successfully");
        response.put("venue", updatedVenue);
        response.put("timestamp", LocalDateTime.now());

        log.info("Venue updated successfully with ID: {}", id);

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 5. DELETE VENUE
    // ======================================================
    @Operation(
            summary = "Delete venue by ID",
            description = "Permanently deletes a venue from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venue not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cannot delete venue with associated events"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVenue(
            @Parameter(description = "Venue ID to delete", example = "1", required = true)
            @PathVariable Long id) {

        log.info("Deleting venue with ID: {}", id);

        venueService.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Venue with ID " + id + " deleted successfully");
        response.put("timestamp", LocalDateTime.now());

        log.info("Venue deleted successfully with ID: {}", id);

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 6. SEARCH VENUES BY CITY
    // ======================================================
    @Operation(
            summary = "Search venues by city",
            description = "Find all venues located in a specific city"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search completed successfully"
            )
    })
    @GetMapping("/search/city/{city}")
    public ResponseEntity<Map<String, Object>> searchVenuesByCity(
            @Parameter(description = "City name", example = "Bogotá", required = true)
            @PathVariable String city) {

        log.info("Searching venues in city: {}", city);

        List<VenueEntity> venues = venueService.findByCity(city);

        Map<String, Object> response = new HashMap<>();
        response.put("city", city);
        response.put("count", venues.size());
        response.put("venues", venues);
        response.put("timestamp", LocalDateTime.now());

        log.info("Found {} venues in city: {}", venues.size(), city);

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 7. GET VENUES BY MINIMUM CAPACITY
    // ======================================================
    @Operation(
            summary = "Find venues by minimum capacity",
            description = "Get all venues with capacity greater than or equal to the specified value"
    )
    @GetMapping("/capacity")
    public ResponseEntity<Map<String, Object>> getVenuesByMinCapacity(
            @Parameter(description = "Minimum capacity", example = "1000", required = true)
            @RequestParam Integer minCapacity) {

        log.info("Searching venues with minimum capacity: {}", minCapacity);

        List<VenueEntity> venues = venueService.findByMinimumCapacity(minCapacity);

        Map<String, Object> response = new HashMap<>();
        response.put("minCapacity", minCapacity);
        response.put("count", venues.size());
        response.put("venues", venues);

        return ResponseEntity.ok(response);
    }
}