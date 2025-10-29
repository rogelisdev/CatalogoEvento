package com.codeup.catalogoDeEventos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class EventoRequest {
    @Schema(description = "El id unico del evento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotBlank(message = "Nombre es obligatorio")
    @Size(min = 3, max = 30, message = "El nombre debe contener entre 3-30 caracteres")
    @Schema(description = "El nombre del evento", example = "Fest Chicken", required = true)
    private String name;

    @NotBlank(message = "El tipo del lugar es obligatorio")
    @Size(min = 3, max = 40, message = "El tipo del lugar debe contener entre 3-40 caracteres")
    @Schema(description = "La descripcion del evento", example = "Ven a conocer al pollo humano", required = true)
    private String descripcion;


    @NotBlank(message = "La fecha es obligatoria")
    @Schema(description = "La fecha y hora del evento", example = "2025-10-28T21:00:00", required = true)
    private Timestamp fecha;

    @NotBlank(message = "Capacidad es obligatoria")
    @Positive(message = "Capacidad debe ser mayor que 0")
    @Schema(description = "Capacidad maxima del evento", example = "1000", required = true)
    private int capacidad;

    @NotBlank(message = "Precio es obligatorio")
    @Positive(message = "El precio tiene que ser mayor a 0")
    @Schema(description = "Precio de la entrada", example = "100", required = true)
    private double precio;
}

