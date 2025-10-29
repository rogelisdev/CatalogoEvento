package com.codeup.catalogoDeEventos.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lugar {
    private long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private int capacidad;

    public Lugar(){}

}
