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
@NoArgsConstructor
public class VenueRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 30, message = "The name must contain between 3–30 characters")
    @Schema(description = "The name of the venue", example = "The Chicken House", required = true)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 3, max = 30, message = "The address must contain between 3–30 characters")
    @Schema(description = "The address of the event", example = "K2 #10 -100", required = true)
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 3, max = 50, message = "The city must contain between 3–50 characters")
    @Schema(description = "The city where the event takes place", example = "Bogota", required = true)
    private String city;

    @NotBlank(message = "Country is required")
    @Size(min = 3, max = 100, message = "The country must contain between 3–100 characters")
    @Schema(description = "The country of the event", example = "Colombia", required = true)
    private String country;

    // KEY FIX: Changed 'int' to 'Integer' and added @NotNull
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    @Schema(description = "Maximum event capacity", example = "1000", required = true)
    private Integer capacity; // Changed from 'int' to 'Integer'
}