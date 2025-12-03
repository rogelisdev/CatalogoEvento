package com.codeup.catalogoDeEventos.service.impl;

import com.codeup.catalogoDeEventos.domain.Venue;
import com.codeup.catalogoDeEventos.dto.VenueRequest;
import com.codeup.catalogoDeEventos.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final List<Venue> array = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public Venue create(VenueRequest request) {
        Venue venue = new Venue();
        venue.setId(counter.getAndIncrement());

        // Mapeo de campos desde el DTO de solicitud (VenueRequest) al objeto de Dominio (Venue)
        venue.setName(request.getName());
        venue.setAddress(request.getAddress());
        venue.setCity(request.getCity());
        venue.setCountry(request.getCountry());
        venue.setCapacity(request.getCapacity());

        array.add(venue);
        return venue;
    }

    @Override
    public List<Venue> getAll() {
        return array;
    }

    @Override
    public Optional<Venue> findById(long id) {
        return array.stream()
                .filter(v -> v.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Venue> update(long id, VenueRequest request) {
        // 1. Buscamos el Venue por ID
        return findById(id).map(venue -> {
            // 2. Si se encuentra, actualizamos sus campos
            venue.setName(request.getName());
            venue.setAddress(request.getAddress());
            venue.setCity(request.getCity());
            venue.setCountry(request.getCountry());
            venue.setCapacity(request.getCapacity());

            return venue;
        });
    }

    @Override
    public boolean delete(long id) {
        // removeIf devuelve 'true' si se eliminó un elemento, 'false' si no se encontró
        return array.removeIf(v -> v.getId() == id);
    }
}
