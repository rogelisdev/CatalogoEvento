package com.codeup.catalogoDeEventos.domain.ports.in.venue;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import jakarta.validation.Valid;

public interface CreateVenueUseCase {

    Venue create(@Valid VenueRequest venue);
}
