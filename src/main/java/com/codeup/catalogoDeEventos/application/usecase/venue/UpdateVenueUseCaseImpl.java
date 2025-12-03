package com.codeup.catalogoDeEventos.application.usecase.venue;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.UpdateVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.VenueRepositoryPort;
import jakarta.validation.Valid;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public UpdateVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Optional<Venue> update(Long id, @Valid VenueRequest updatedVenue) {
        return venueRepository.findById(id)
                .map(existing -> {

                    existing.setName(updatedVenue.getName());
                    existing.setAddress(updatedVenue.getAddress());
                    existing.setCity(updatedVenue.getCity());
                    existing.setCountry(updatedVenue.getCountry());
                    existing.setCapacity(updatedVenue.getCapacity());

                    return venueRepository.create(existing);
                });
    }
}
