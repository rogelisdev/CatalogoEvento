package com.codeup.catalogoDeEventos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VenueResponse {

    private long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private int capacidad;
}
