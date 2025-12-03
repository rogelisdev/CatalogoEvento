package com.codeup.catalogoDeEventos.domain.models;

public class Venue {

    private Long id;
    private String name;
    private String city;
    private String address;
    private String country;
    private int capacity;

    public Venue(Long id, String name, String city, String address, String country, int capacity) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.country = country;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
