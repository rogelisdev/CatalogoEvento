package com.codeup.catalogoDeEventos.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Evento {
    private long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private int capacidad;
    private long idLugar;
    private double precio;

    public Evento() {
    }
}