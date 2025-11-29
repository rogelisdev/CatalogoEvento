package com.codeup.catalogoDeEventos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private long idVenue;
    private double price;
}