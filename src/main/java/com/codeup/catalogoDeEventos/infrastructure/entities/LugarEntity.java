package com.codeup.catalogoDeEventos.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "lugares",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombre") // Evita nombres duplicados en BD
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LugarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🟢 Validaciones
    @NotBlank(message = "El nombre del lugar es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no debe exceder 100 caracteres")
    private String ciudad;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 150, message = "La dirección no debe exceder 150 caracteres")
    private String direccion;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 100, message = "El país no debe exceder 100 caracteres")
    private String pais;

    @Min(value = 1, message = "La capacidad mínima debe ser al menos 1 persona")
    private int capacidad;

    // 🟢 Relación bidireccional con EventoEntity
    @OneToMany(mappedBy = "lugar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoEntity> eventos;
}
