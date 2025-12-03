package com.codeup.catalogoDeEventos.application.dto.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VenueResponse {

    private long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private int capacity;
}
