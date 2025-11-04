package com.codeup.catalogoDeEventos.dto;

import com.codeup.catalogoDeEventos.domain.Evento;
import com.codeup.catalogoDeEventos.domain.Lugar;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// Usamos este constructor para el DTO simple
public class EventoDetalleResponse {
    private long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private int capacidad;
    private double precio;

    // Campo para el objeto combinado (Lugar)
    private Lugar lugar;

    // Constructor para la combinación (JOIN)
    public EventoDetalleResponse(Evento evento, Lugar lugar) {
        this.id = evento.getId();
        this.nombre = evento.getNombre();
        this.descripcion = evento.getDescripcion();
        this.fecha = evento.getFecha();
        this.capacidad = evento.getCapacidad();
        this.precio = evento.getPrecio();
        this.lugar = lugar; // <--- ¡Combinación de datos!
    }
}