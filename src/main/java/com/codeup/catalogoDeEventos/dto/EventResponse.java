package com.codeup.catalogoDeEventos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private long idVenue;
    private double price;

}

