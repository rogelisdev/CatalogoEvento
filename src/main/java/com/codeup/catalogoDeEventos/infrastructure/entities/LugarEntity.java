package com.codeup.catalogoDeEventos.infrastructure.entities;

import com.codeup.catalogoDeEventos.domain.models.Lugar;
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

public class LugarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String pais;
    private int cantidad;
    //Relación bidireccional con EventoEntity
    @OneToMany(mappedBy = "lugar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoEntity> eventos;

    public LugarEntity(Long id, String nombre, String ciudad, String direccion, String pais, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.pais = pais;
        this.cantidad = cantidad;
    }

    public static LugarEntity fromDomainModel(Lugar lugar){
        return new LugarEntity(lugar.getId(), lugar.getNombre(), lugar.getCiudad(), lugar.getDireccion(), lugar.getPais(), lugar.getCantidad());
    }

    public Lugar toDomainModel(){
        return new Lugar(id, nombre, ciudad, direccion, pais, cantidad);
    }
}
