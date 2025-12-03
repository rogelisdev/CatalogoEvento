package com.codeup.catalogoDeEventos.domain.ports.in.venue;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import jakarta.validation.Valid;

import java.util.Optional;

public interface UpdateVenueUseCase {
    Optional<Venue> update(Long id, @Valid VenueRequest venue);
}
