package com.codeup.catalogoDeEventos.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class EventoReponse {
    private long id;
    private String nombre;
    private String tipo;
    private Timestamp fehca_inicio;
    private Timestamp fecha_fin;
    private int capacidad;
    private double precio;

    public EventoReponse(long id, String nombre, String tipo, Timestamp fehca_inicio, Timestamp fecha_fin, int capacidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fehca_inicio = fehca_inicio;
        this.fecha_fin = fecha_fin;
        this.capacidad = capacidad;
        this.precio = precio;
    }
}

