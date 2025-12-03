package com.codeup.catalogoDeEventos.application.dto.venue;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Size(min = 3, max = 30, message = "The name must contain between 3 and 30 characters")
    @Schema(description = "The name of the venue", example = "La Casa del Pollo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 3, max = 30, message = "The address must contain between 3 and 30 characters")
    @Schema(description = "Venue address", example = "K2 #10-100", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 3, max = 50, message = "The city must contain between 3 and 50 characters")
    @Schema(description = "City where the venue is located", example = "Bogota", requiredMode = Schema.RequiredMode.REQUIRED)
    private String city;

    @NotBlank(message = "Country is required")
    @Size(min = 3, max = 100, message = "The country must contain between 3 and 100 characters")
    @Schema(description = "Country of the venue", example = "Colombia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String country;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    @Schema(description = "Maximum capacity of the venue", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer capacity;
}
