package com.codeup.catalogoDeEventos.dto;

import com.codeup.catalogoDeEventos.domain.VenueEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder // Added Builder for convenient object creation in the service/controller
public class EventResponse { // Response DTO for Event listing or detail

    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private double price;

    // Note: It's usually better practice to return a VenueResponse DTO instead of the
    // full VenueEntity, but we keep the Entity here for simplicity based on the source code.
    private VenueEntity venue;

    // This field is redundant if 'venue' is present, but kept for direct translation
    private long venueId;
}