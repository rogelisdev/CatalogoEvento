package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface VenueRepositoryPort {

    Venue create(Venue venue);

    Optional<Venue> update(Venue venue);

    boolean delete(Long id);

    List<Venue> findAll();

    Optional<Venue> findById(Long id);
}
