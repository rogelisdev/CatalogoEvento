package com.codeup.catalogoDeEventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity { // Renamed from EventoEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The event name is mandatory")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters")
    @Column(name = "name") // Added @Column for clarity, matches the uniqueConstraint
    private String name; // Renamed from nombre

    @NotBlank(message = "The description is mandatory")
    @Size(max = 500, message = "The description cannot exceed 500 characters")
    private String description; // Renamed from descripcion

    @Future(message = "The event date must be in the future")
    @Column(name = "date") // Added for clarity
    private LocalDateTime date; // Renamed from fecha

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity; // Renamed from capacidad

    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    private double price; // Renamed from precio

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false) // Renamed from id_lugar
    private VenueEntity venue; // Renamed from lugar, assuming the related class is VenueEntity
}