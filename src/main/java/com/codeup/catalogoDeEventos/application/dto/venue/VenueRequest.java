package com.codeup.catalogoDeEventos.application.dto.venue;

import com.codeup.catalogoDeEventos.application.validation.ValidationGroups;
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

    @NotBlank(message = "{validation.venue.name.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Size(min = 3, max = 30, message = "{validation.venue.name.size}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "The name of the venue", example = "La Casa del Pollo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "{validation.venue.address.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Size(min = 3, max = 30, message = "{validation.venue.address.size}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "Venue address", example = "K2 #10-100", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @NotBlank(message = "{validation.venue.city.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Size(min = 3, max = 50, message = "{validation.venue.city.size}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "City where the venue is located", example = "Bogota", requiredMode = Schema.RequiredMode.REQUIRED)
    private String city;

    @NotBlank(message = "{validation.venue.country.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Size(min = 3, max = 100, message = "{validation.venue.country.size}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "Country of the venue", example = "Colombia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String country;

    @NotNull(message = "{validation.venue.capacity.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Positive(message = "{validation.venue.capacity.positive}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "Maximum capacity of the venue", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer capacity;
}
