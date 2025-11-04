package com.codeup.catalogoDeEventos.dto;

import com.codeup.catalogoDeEventos.domain.LugarEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoDetalleResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private int capacidad;
    private double precio;

    private LugarEntity lugar;
}
