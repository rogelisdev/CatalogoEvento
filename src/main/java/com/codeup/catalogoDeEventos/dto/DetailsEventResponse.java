package com.codeup.catalogoDeEventos.dto;

import com.codeup.catalogoDeEventos.domain.Event;
import com.codeup.catalogoDeEventos.domain.Venue;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class DetailsEventResponse {
    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private double price;

    private Venue venue;

    // Constructor para la combinación (JOIN)
    public DetailsEventResponse(Event event, Venue venue) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.date = event.getDate();
        this.capacity = event.getCapacity();
        this.price = event.getPrice();
        this.venue = venue; // <--- ¡Combinación de datos!
    }
}