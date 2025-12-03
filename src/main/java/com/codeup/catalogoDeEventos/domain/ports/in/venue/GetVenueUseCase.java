package com.codeup.catalogoDeEventos.domain.ports.in.venue;

import com.codeup.catalogoDeEventos.domain.models.Venue;

import java.util.List;
import java.util.Optional;

public interface GetVenueUseCase {
    Optional<Venue> getById(Long id);
    List<Venue> getAll();

}
