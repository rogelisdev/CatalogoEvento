package com.codeup.catalogoDeEventos.infrastructure.entities;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "venues",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String address;
    private String country;
    private int capacity;

    // Bidirectional relationship with EventEntity
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events;

    // Constructor without ID (for creation)
    public VenueEntity(String name, String city, String address, String country, int capacity) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.country = country;
        this.capacity = capacity;
    }

    // Mapper from Domain Model (Domain object)
    public static VenueEntity fromDomainModel(Venue venue) {
        VenueEntity entity = new VenueEntity(
                venue.getName(),
                venue.getCity(),
                venue.getAddress(),
                venue.getCountry(),
                venue.getCapacity()
        );
        entity.setId(venue.getId()); // solo asigna ID si existe
        return entity;
    }


    // Mapper to Domain Model
    public Venue toDomainModel() {
        return new Venue(
                id,
                name,
                city,
                address,
                country,
                capacity
        );
    }
}
