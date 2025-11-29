package com.codeup.catalogoDeEventos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venue {
    private long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private int capacity;

}
