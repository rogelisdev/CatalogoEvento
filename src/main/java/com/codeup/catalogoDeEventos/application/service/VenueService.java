package com.codeup.catalogoDeEventos.application.service;

import com.codeup.catalogoDeEventos.application.dto.venue.VenueRequest;
import com.codeup.catalogoDeEventos.domain.models.Venue;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.CreateVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.UpdateVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.DeleteVenueUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.venue.GetVenueUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService implements
        CreateVenueUseCase,
        UpdateVenueUseCase,
        DeleteVenueUseCase,
        GetVenueUseCase {

    private final CreateVenueUseCase createVenueUseCase;
    private final UpdateVenueUseCase updateVenueUseCase;
    private final DeleteVenueUseCase deleteVenueUseCase;
    private final GetVenueUseCase getVenueUseCase;

    public VenueService(
            CreateVenueUseCase createVenueUseCase,
            UpdateVenueUseCase updateVenueUseCase,
            DeleteVenueUseCase deleteVenueUseCase,
            GetVenueUseCase getVenueUseCase
    ) {
        this.createVenueUseCase = createVenueUseCase;
        this.updateVenueUseCase = updateVenueUseCase;
        this.deleteVenueUseCase = deleteVenueUseCase;
        this.getVenueUseCase = getVenueUseCase;
    }


    @Override
    public Venue create(Venue venue) {
        return createVenueUseCase.create(venue);
    }

    @Override
    public boolean delete(Long id) {
        deleteVenueUseCase.delete(id);
        return false;
    }

    @Override
    public Optional<Venue> getById(Long id) {
        return getVenueUseCase.getById(id);
    }

    @Override
    public List<Venue> getAll() {
        return getVenueUseCase.getAll();
    }

    @Override
    public Optional<Venue> update(Long id, @Valid VenueRequest venue) {
        return updateVenueUseCase.update(id, venue);
    }
}
