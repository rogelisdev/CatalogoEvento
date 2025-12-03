package com.codeup.catalogoDeEventos.domain.models;

import java.time.LocalDateTime;

public class Event {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private int capacity;
    private double price;

    public Event(Long id, String name, String description, LocalDateTime date, int capacity, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.capacity = capacity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
