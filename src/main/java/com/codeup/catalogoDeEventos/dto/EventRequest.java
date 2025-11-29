package com.codeup.catalogoDeEventos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 30, message = "The name must contain between 3–30 characters")
    @Schema(description = "The name of the event", example = "Fest Chicken", required = true)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 40, message = "The description must contain between 3–40 characters")
    @Schema(description = "The event description", example = "Come meet the human chicken", required = true)
    private String description;

    @NotNull(message = "The date is required")
    @Schema(description = "The date and time of the event", example = "2025-10-28T21:00:00", required = true)
    private LocalDateTime date;

    @Positive(message = "Capacity must be greater than 0")
    @Schema(description = "Maximum event capacity", example = "1000", required = true)
    private int capacity;

    @Positive(message = "Venue ID must be greater than 0")
    @Schema(description = "ID of the venue for the event", example = "1", required = true)
    private long idVenue;

    @Positive(message = "Price must be greater than 0")
    @Schema(description = "Ticket price", example = "100", required = true)
    private double price;
}

