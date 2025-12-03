package com.codeup.catalogoDeEventos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @NotBlank(message = "The event name is mandatory")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters")
    @Schema(description = "Name of the event", example = "Rock Concert", required = true)
    private String name;

    @NotBlank(message = "The description is mandatory")
    @Size(max = 500, message = "The description cannot exceed 500 characters")
    @Schema(description = "Detailed description of the event", example = "International rock show", required = true)
    private String description;

    @NotNull(message = "The event date is mandatory")
    @Future(message = "The event date must be in the future")
    @Schema(description = "Date and time of the event", example = "2025-11-20T20:00:00", required = true)
    private LocalDateTime date;

    @NotNull(message = "Capacity is mandatory")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Schema(description = "Maximum capacity of attendees", example = "1000", required = true)
    private Integer capacity;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    @Schema(description = "Ticket price", example = "250.0", required = true)
    private Double price;

    @NotNull(message = "Venue ID is mandatory")
    @Positive(message = "Venue ID must be positive")
    @Schema(description = "ID of the venue where the event will take place", example = "2", required = true)
    private Long venueId;
}