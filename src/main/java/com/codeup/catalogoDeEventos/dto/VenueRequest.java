package com.codeup.catalogoDeEventos.dto;

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

    @NotBlank(message = "The name is mandatory")
    @Size(min = 3, max = 100, message = "The name must contain between 3 and 100 characters") // Adjusted max size for consistency with Entity
    @Schema(description = "The name of the venue", example = "The Chicken House", required = true)
    private String name;

    @NotBlank(message = "The address is mandatory")
    @Size(min = 3, max = 150, message = "The address must contain between 3 and 150 characters") // Adjusted max size for consistency with Entity
    @Schema(description = "The physical address of the event venue", example = "K2 #10 -100", required = true)
    private String address;

    @NotBlank(message = "The city is mandatory")
    @Size(min = 3, max = 100, message = "The city must contain between 3 and 100 characters") // Adjusted max size for consistency with Entity
    @Schema(description = "The city where the venue is located", example = "Bogota", required = true)
    private String city;

    @NotBlank(message = "The country is mandatory")
    @Size(min = 3, max = 100, message = "The country must contain between 3 and 100 characters")
    @Schema(description = "The country of the venue", example = "Colombia", required = true)
    private String country;


    // KEY CORRECTION: Changed 'int' to 'Integer' and added @NotNull
    @NotNull(message = "Capacity is mandatory")
    @Positive(message = "Capacity must be greater than 0")
    @Schema(description = "Maximum capacity of the venue", example = "1000", required = true)
    private Integer capacity; // Changed from 'int' to 'Integer' to allow null checks with @NotNull
}