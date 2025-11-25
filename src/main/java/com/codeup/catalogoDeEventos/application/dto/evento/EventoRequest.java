package com.codeup.catalogoDeEventos.application.dto.evento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class  EventoRequest {

    @NotBlank(message = "Nombre es obligatorio")
    @Size(min = 3, max = 30, message = "El nombre debe contener entre 3-30 caracteres")
    @Schema(description = "El nombre del evento", example = "Fest Chicken", required = true)
    private String nombre;

    @NotBlank(message = "El tipo del lugar es obligatorio")
    @Size(min = 3, max = 40, message = "El tipo del lugar debe contener entre 3-40 caracteres")
    @Schema(description = "La descripcion del evento", example = "Ven a conocer al pollo humano", required = true)
    private String descripcion;


    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "La fecha y hora del evento", example = "2025-10-28T21:00:00", required = true)
    private LocalDateTime fecha;

    @Positive(message = "Capacidad debe ser mayor que 0")
    @Schema(description = "Capacidad maxima del evento", example = "1000", required = true)
    private int capacidad;

    @Positive(message = "El id del lugar debe ser mayor que 0")
    @Schema(description = "ID del lugar del evento", example = "1", required = true)
    private long idLugar;

    @Positive(message = "El precio tiene que ser mayor a 0")
    @Schema(description = "Precio de la entrada", example = "100", required = true)
    private double precio;
}

