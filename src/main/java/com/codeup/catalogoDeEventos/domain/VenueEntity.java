package com.codeup.catalogoDeEventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "venues",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name") }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🟢 Validations
    @NotBlank(message = "The venue name is mandatory")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters")
    @Column(name = "name") // Added for clarity and to match uniqueConstraints
    private String name;

    @NotBlank(message = "The city is mandatory")
    @Size(max = 100, message = "The city cannot exceed 100 characters")
    private String city;

    @NotBlank(message = "The address is mandatory")
    @Size(max = 150, message = "The address cannot exceed 150 characters")
    private String address;

    @NotBlank(message = "The country is mandatory")
    @Size(max = 100, message = "The country cannot exceed 100 characters")
    private String country;

    @Min(value = 1, message = "The minimum capacity must be at least 1 person")
    private int capacity;

    // 🟢 Bidirectional relationship with EventEntity
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events;
}