package com.codeup.catalogoDeEventos.application.dto.event;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventResponse {
    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private long placeId;
    private double price;

    private VenueResponse venue;
}
