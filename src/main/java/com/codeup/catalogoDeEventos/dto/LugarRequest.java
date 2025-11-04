package com.codeup.catalogoDeEventos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // NUEVA IMPORTACIÓN
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // <--- ¡Añadir esta anotación!
public class LugarRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 30, message = "El nombre debe contener entre 3-30 caracteres")
    @Schema(description = "El nombre del lugar", example = "La casa del pollo", required = true)
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(min = 3, max = 30, message = "La direccion debe contener entre 3-30 caracteres")
    @Schema(description = "La direccion del evento", example = "K2 #10 -100", required = true)
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 3, max = 50, message = "La ciudad debe contener entre 3-50 caracteres")
    @Schema(description = "La ciudad donde se encuentra el evento", example = "Bogota", required = true)
    private String ciudad;

    @NotBlank(message = "El pais es obligatorio")
    @Size(min = 3, max = 100, message = "El pais debe contener entre 3-100 caracteres")
    @Schema(description = "El pais del evento", example = "Colombia", required = true)
    private String pais;


    // CORRECCIÓN CLAVE: Se cambió 'int' a 'Integer' y se añadió @NotNull
    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "Capacidad debe ser mayor que 0")
    @Schema(description = "Capacidad maxima del evento", example = "1000", required = true)
    private Integer capacidad; // Cambiado de 'int' a 'Integer'
}