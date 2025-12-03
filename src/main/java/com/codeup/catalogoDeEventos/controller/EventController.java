package com.codeup.catalogoDeEventos.controller;

import com.codeup.catalogoDeEventos.advice.ErrorResponse;
import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.EventEntity;
import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
import com.codeup.catalogoDeEventos.dto.EventRequest;
import com.codeup.catalogoDeEventos.service.EventService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Event Management", description = "Complete API for managing events with pagination, filters, and advanced search capabilities")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventController {

    private final EventService eventService;

    // ======================================================
    // 1. CREATE EVENT
    // ======================================================
    @Operation(
            summary = "Create new event",
            description = "Registers a new event with its complete information and validates the associated venue existence."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Event created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventEntity.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                            {
                                              "message": "Event created successfully",
                                              "event": {
                                                "id": 1,
                                                "name": "Rock Concert",
                                                "description": "International rock show",
                                                "date": "2025-11-20T20:00:00",
                                                "capacity": 1000,
                                                "price": 250.0,
                                                "venue": {
                                                  "id": 2,
                                                  "name": "National Stadium"
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data in the request body",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
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
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEvent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Event data to register",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EventRequest.class),
                            examples = @ExampleObject(
                                    name = "Complete Event",
                                    value = """
                                            {
                                              "name": "Rock Concert",
                                              "description": "Amazing international rock show",
                                              "date": "2025-11-20T20:00:00",
                                              "capacity": 1000,
                                              "price": 250.0,
                                              "venueId": 2
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody EventRequest request) {

        log.info("Creating new event: {}", request.getName());

        EventEntity newEvent = eventService.save(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event created successfully");
        response.put("event", newEvent);
        response.put("timestamp", LocalDateTime.now());

        log.info("Event created successfully with ID: {}", newEvent.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ======================================================
    // 2. LIST EVENTS (WITH PAGINATION AND FILTERS)
    // ======================================================
    @Operation(
            summary = "List events with pagination and filters",
            description = """
                    Retrieve a paginated list of events with optional filters:
                    - Filter by city (venue location)
                    - Filter by category (if available)
                    - Filter by start date (events from this date onwards)
                    - Sort by multiple fields
                    - Pageable support for large datasets
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of events successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> listEvents(
            @Parameter(description = "Filter by city name (case-insensitive)", example = "Bogotá")
            @RequestParam(required = false) String city,

            @Parameter(description = "Filter by event category", example = "Music")
            @RequestParam(required = false) String category,

            @Parameter(
                    description = "Filter by start date (ISO-8601 format: YYYY-MM-DDTHH:MM:SS)",
                    example = "2025-11-01T00:00:00"
            )
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort field", example = "date")
            @RequestParam(defaultValue = "date") String sortBy,

            @Parameter(description = "Sort direction (ASC or DESC)", example = "ASC")
            @RequestParam(defaultValue = "ASC") String direction) {

        log.info("Listing events - page: {}, size: {}, city: {}, category: {}, startDate: {}",
                page, size, city, category, startDate);

        // Create pageable with sorting
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        // Get events
        Page<EventEntity> events = (Page<EventEntity>) eventService.findAll(pageable, city, category, startDate);

        // Build response with metadata
        Map<String, Object> response = new HashMap<>();
        response.put("events", events.getContent());
        response.put("currentPage", events.getNumber());
        response.put("totalItems", events.getTotalElements());
        response.put("totalPages", events.getTotalPages());
        response.put("pageSize", events.getSize());
        response.put("hasNext", events.hasNext());
        response.put("hasPrevious", events.hasPrevious());

        log.info("Retrieved {} events from {} total", events.getNumberOfElements(), events.getTotalElements());

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 3. GET DETAILS BY ID
    // ======================================================
    @Operation(
            summary = "Get event details by ID",
            description = "Returns complete information of an event including all venue details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event successfully found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailsEventResponse.class),
                            examples = @ExampleObject(
                                    name = "Event Details",
                                    value = """
                                            {
                                              "id": 1,
                                              "name": "Rock Concert",
                                              "description": "International rock show",
                                              "date": "2025-11-20T20:00:00",
                                              "capacity": 1000,
                                              "price": 250.0,
                                              "venue": {
                                                "id": 2,
                                                "name": "National Stadium",
                                                "address": "K2 #10-100",
                                                "city": "Bogotá",
                                                "country": "Colombia",
                                                "capacity": 5000
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetailsEventResponse> getEventDetails(
            @Parameter(description = "Event ID", example = "1", required = true)
            @PathVariable Long id) {

        log.info("Fetching details for event with ID: {}", id);

        DetailsEventResponse detail = eventService.findDetailsById(id)
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event", id);
                });

        log.info("Event details retrieved successfully for ID: {}", id);

        return ResponseEntity.ok(detail);
    }

    // ======================================================
    // 4. UPDATE EVENT
    // ======================================================
    @Operation(
            summary = "Update an existing event",
            description = "Modifies all fields of an existing event. Validates that both event and venue exist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventEntity.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event or Venue not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data in request body",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEvent(
            @Parameter(description = "Event ID to update", example = "1", required = true)
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated event data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EventRequest.class))
            )
            @Valid @RequestBody EventRequest request) {

        log.info("Updating event with ID: {}", id);

        EventEntity updatedEvent = eventService.update(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event updated successfully");
        response.put("event", updatedEvent);
        response.put("timestamp", LocalDateTime.now());

        log.info("Event updated successfully with ID: {}", id);

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 5. PARTIAL UPDATE EVENT (PATCH)
    // ======================================================
    @Operation(
            summary = "Partially update an event",
            description = "Updates only the provided fields of an existing event"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event partially updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> partialUpdateEvent(
            @Parameter(description = "Event ID", example = "1", required = true)
            @PathVariable Long id,

            @RequestBody Map<String, Object> updates) {

        log.info("Partially updating event with ID: {}", id);

        // TODO: Implement partial update logic in service
        // For now, return not implemented
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Partial update not yet implemented");
        response.put("eventId", id);

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }

    // ======================================================
    // 6. DELETE EVENT
    // ======================================================
    @Operation(
            summary = "Delete event by ID",
            description = "Permanently deletes an event from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "message": "Event with ID 1 deleted successfully",
                                              "timestamp": "2025-12-03T15:30:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEvent(
            @Parameter(description = "Event ID to delete", example = "1", required = true)
            @PathVariable Long id) {

        log.info("Deleting event with ID: {}", id);

        eventService.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event with ID " + id + " deleted successfully");
        response.put("timestamp", LocalDateTime.now());

        log.info("Event deleted successfully with ID: {}", id);

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 7. COUNT EVENTS
    // ======================================================
    @Operation(
            summary = "Count total events",
            description = "Returns the total number of events in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Count retrieved successfully"
            )
    })
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countEvents() {
        log.info("Counting total events");

        // Get all events without filters
        Page<EventEntity> events = (Page<EventEntity>) eventService.findAll(
                PageRequest.of(0, 1),
                null, null, null
        );

        Map<String, Object> response = new HashMap<>();
        response.put("totalEvents", events.getTotalElements());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    // ======================================================
    // 8. SEARCH EVENTS BY NAME
    // ======================================================
    @Operation(
            summary = "Search events by name",
            description = "Search events using a keyword that matches the event name (case-insensitive)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search completed successfully"
            )
    })
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchEventsByName(
            @Parameter(description = "Search keyword", example = "Rock", required = true)
            @RequestParam String keyword,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        log.info("Searching events with keyword: {}", keyword);

        // This would require a new service method
        // For now, return a placeholder response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Search functionality to be implemented");
        response.put("keyword", keyword);
        response.put("suggestion", "Use GET /api/events with filters instead");

        return ResponseEntity.ok(response);
    }
}