package com.codeup.catalogoDeEventos.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class EventoReponse {
    private long id;
    private String nombre;
    private String descripcion;
    private Timestamp fecha;
    private int capacidad;
    private long idLugar;
    private double precio;

    public EventoReponse(long id, String nombre, String descripcion, Timestamp fecha, int capacidad, long idLugar, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.capacidad = capacidad;
        this.idLugar = idLugar;
        this.precio = precio;
    }
}

