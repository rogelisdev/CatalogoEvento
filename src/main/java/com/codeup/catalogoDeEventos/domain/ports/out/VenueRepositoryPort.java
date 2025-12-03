package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.domain.models.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueRepositoryPort {

    Venue create(Venue venue);

    Optional<Venue> update(Venue venue);

    boolean delete(Long id);

    List<Venue> findAll();

    Optional<Venue> findById(Long id);
}
