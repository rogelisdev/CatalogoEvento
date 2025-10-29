package com.codeup.catalogoDeEventos.dto;

import lombok.Getter;

@Getter
public class LugarRespose {

    private long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private int capacidad;

    public LugarRespose(long id, String nombre, String direccion, String ciudad, String pais, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.capacidad = capacidad;
    }
}
