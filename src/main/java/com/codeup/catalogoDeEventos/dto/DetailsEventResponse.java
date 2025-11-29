package com.codeup.catalogoDeEventos.dto;

import com.codeup.catalogoDeEventos.domain.Event;
import com.codeup.catalogoDeEventos.domain.Venue;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// Usamos este constructor para el DTO simple
public class DetailsEventResponse {
    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private double price;

    // Campo para el objeto combinado (Lugar)
    private Venue venue;

    // Constructor para la combinación (JOIN)
    public DetailsEventResponse(Event evento, Venue venue) {
        this.id = evento.getId();
        this.name = evento.getName();
        this.description = evento.getDescription();
        this.date = evento.getDate();
        this.capacity = evento.getCapacity();
        this.price = evento.getPrice();
        this.venue = venue; // <--- ¡Combinación de datos!
    }
}