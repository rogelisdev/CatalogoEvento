package com.codeup.catalogoDeEventos.application.dto.event;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDetailResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private double price;

    private VenueResponse venue;
}
