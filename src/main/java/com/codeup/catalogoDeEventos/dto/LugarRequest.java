package com.codeup.catalogoDeEventos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LugarRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 30, message = "El nombre debe contener entre 3-30 caracteres")
    @Schema(description = "El nombre del lugar", example = "La casa del pollo", required = true)
    private String name;

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

    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "El precio del lugar", example = "100", required = true)
    private double precio;

}
