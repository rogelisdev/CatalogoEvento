package com.codeup.catalogoDeEventos.infrastructure.entities;

import com.codeup.catalogoDeEventos.domain.models.Evento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private int cantidad;
    private double precio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lugar", nullable = false)
    private LugarEntity lugar;

    public EventoEntity(Long id, String nombre, String descripcion, LocalDateTime fecha, int capacidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cantidad = capacidad;
        this.precio = precio;
    }
    public static EventoEntity fromDomainModel(Evento evento){
        return new EventoEntity(evento.getId(), evento.getNombre(), evento.getDescripcion(), evento.getFecha(), evento.getCantidad(), evento.getPrecio());
    }

    public Evento toDomainModel(){
        return new Evento(id, nombre, descripcion, fecha, cantidad, precio);
    }

}