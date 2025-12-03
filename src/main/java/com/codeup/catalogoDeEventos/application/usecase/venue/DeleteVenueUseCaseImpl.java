package com.codeup.catalogoDeEventos.application.usecase.venue;

import com.codeup.catalogoDeEventos.domain.ports.in.venue.DeleteVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.VenueRepositoryPort;

public class DeleteVenueUseCaseImpl implements DeleteVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public DeleteVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }


    @Override
    public boolean delete(Long id) {
        venueRepository.delete(id);
        return false;
    }
}
