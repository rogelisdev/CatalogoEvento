package com.codeup.catalogoDeEventos.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @Future(message = "La fecha del evento debe ser futura")
    private LocalDateTime fecha;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private int capacidad;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private double precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lugar", nullable = false)
    private LugarEntity lugar;
}
