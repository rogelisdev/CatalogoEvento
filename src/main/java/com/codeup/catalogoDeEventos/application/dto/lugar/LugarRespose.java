package com.codeup.catalogoDeEventos.application.dto.lugar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LugarRespose {

    private long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private int capacidad;
}
