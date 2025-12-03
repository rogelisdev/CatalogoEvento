package com.codeup.catalogoDeEventos.application.dto.event;

import com.codeup.catalogoDeEventos.application.validation.FutureDate;
import com.codeup.catalogoDeEventos.application.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequest {

    @NotBlank(message = "{validation.event.name.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Size(min = 3, max = 30, message = "{validation.event.name.size}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "The name of the event", example = "Fest Chicken", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "{validation.event.description.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Size(min = 3, max = 40, message = "{validation.event.description.size}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "Event description", example = "Come meet the human chicken", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "{validation.event.date.required}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @FutureDate(groups = ValidationGroups.Create.class)
    @Schema(description = "Event date and time", example = "2025-10-28T21:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime date;

    @Positive(message = "{validation.event.capacity.positive}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "Maximum event capacity", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
    private int capacity;

    @Positive(message = "{validation.event.placeid.positive}", groups = ValidationGroups.Create.class)
    @Schema(description = "ID of the event place", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long placeId;

    @Positive(message = "{validation.event.price.positive}", groups = { ValidationGroups.Create.class,
            ValidationGroups.Update.class })
    @Schema(description = "Ticket price", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private double price;
}
