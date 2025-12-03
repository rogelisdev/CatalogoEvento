package com.codeup.catalogoDeEventos.dto;

import com.codeup.catalogoDeEventos.domain.EventEntity;
import com.codeup.catalogoDeEventos.domain.VenueEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsEventResponse {

    @Schema(description = "Event ID", example = "1")
    private Long id;

    @Schema(description = "Event name", example = "Rock Concert")
    private String name;

    @Schema(description = "Event description", example = "International rock show")
    private String description;

    @Schema(description = "Event date and time", example = "2025-11-20T20:00:00")
    private LocalDateTime date;

    @Schema(description = "Maximum capacity", example = "1000")
    private Integer capacity;

    @Schema(description = "Ticket price", example = "250.0")
    private Double price;

    @Schema(description = "Venue details")
    private VenueDetails venue;

    // Constructor that maps from entities
    public DetailsEventResponse(EventEntity event, VenueEntity venue) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.date = event.getDate();
        this.capacity = event.getCapacity();
        this.price = event.getPrice();
        this.venue = new VenueDetails(venue);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VenueDetails {
        @Schema(description = "Venue ID", example = "2")
        private Long id;

        @Schema(description = "Venue name", example = "National Stadium")
        private String name;

        @Schema(description = "Venue address", example = "K2 #10-100")
        private String address;

        @Schema(description = "City", example = "Bogotá")
        private String city;

        @Schema(description = "Country", example = "Colombia")
        private String country;

        @Schema(description = "Venue capacity", example = "5000")
        private Integer capacity;

        // Constructor from VenueEntity
        public VenueDetails(VenueEntity venue) {
            this.id = venue.getId();
            this.name = venue.getName();
            this.address = venue.getAddress();
            this.city = venue.getCity();
            this.country = venue.getCountry();
            this.capacity = venue.getCapacity();
        }
    }
}