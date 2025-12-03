package com.codeup.catalogoDeEventos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder; // Added Builder for convenient object creation

@Getter
@AllArgsConstructor
@Builder // Recommended for easy construction from Entity in Service/Controller
public class VenueResponse {

    private long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private int capacity;
}