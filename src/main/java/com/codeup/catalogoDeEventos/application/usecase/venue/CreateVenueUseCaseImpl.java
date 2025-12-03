package com.codeup.catalogoDeEventos.application.usecase.venue;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.CreateVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.VenueRepositoryPort;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CreateVenueUseCaseImpl implements CreateVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public CreateVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue create(@Valid VenueRequest venue) {
        Venue domainVenue = new Venue(
                null,
                venue.getName(),
                venue.getCity(),
                venue.getAddress(),
                venue.getCountry(),
                venue.getCapacity());
        return venueRepository.create(domainVenue);
    }
}
