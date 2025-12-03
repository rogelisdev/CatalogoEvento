package com.codeup.catalogoDeEventos.infrastructure.entities;

import com.codeup.catalogoDeEventos.domain.models.Event;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private VenueEntity venue;

    // Constructor without ID (for creation)
    public EventEntity(String name, String description, LocalDateTime date, int capacity, double price, VenueEntity venue) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.capacity = capacity;
        this.price = price;
        this.venue = venue;
    }

    // Mapper from Domain Model
    public static EventEntity fromDomainModel(Event event, VenueEntity venueEntity) {
        EventEntity entity = new EventEntity(
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getCapacity(),
                event.getPrice(),
                venueEntity
        );
        if (event.getId() != null) {
            entity.setId(event.getId()); // only set ID if updating
        }
        return entity;
    }

    // Mapper to Domain Model
    public Event toDomainModel() {
        return new Event(
                this.id,
                this.name,
                this.description,
                this.date,
                this.capacity,
                this.price
        );
    }
}
