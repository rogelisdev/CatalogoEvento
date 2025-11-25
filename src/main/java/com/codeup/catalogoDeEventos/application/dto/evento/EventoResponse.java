package com.codeup.catalogoDeEventos.application.dto.evento;

import com.codeup.catalogoDeEventos.infrastructure.entities.LugarEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventoResponse {
    private long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private int capacidad;
    private long idLugar;
    private double precio;

    private LugarEntity lugar;
}

