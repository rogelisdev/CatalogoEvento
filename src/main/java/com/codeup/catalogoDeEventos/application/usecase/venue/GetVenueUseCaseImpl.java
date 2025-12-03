package com.codeup.catalogoDeEventos.application.usecase.venue;

import com.codeup.catalogoDeEventos.domain.models.Venue;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.GetVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.VenueRepositoryPort;

import java.util.List;
import java.util.Optional;

public class GetVenueUseCaseImpl implements GetVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public GetVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Optional<Venue> getById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    public List<Venue> getAll() {
        return venueRepository.findAll();
    }
}
