package com.codeup.catalogoDeEventos.infrastructure.adapters;

import com.codeup.catalogoDeEventos.domain.models.Venue;
import com.codeup.catalogoDeEventos.domain.ports.out.VenueRepositoryPort;
import com.codeup.catalogoDeEventos.infrastructure.entities.VenueEntity;
import com.codeup.catalogoDeEventos.infrastructure.repositories.JpaVenueRepository;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaVenueRepositoryAdapter implements VenueRepositoryPort {

    private final JpaVenueRepository jpaVenueRepository;

    public JpaVenueRepositoryAdapter(JpaVenueRepository jpaVenueRepository) {
        this.jpaVenueRepository = jpaVenueRepository;
    }

    @Override
    public Venue create(@Valid Venue venue) {
        VenueEntity entity = VenueEntity.fromDomainModel(venue);
        VenueEntity savedEntity = jpaVenueRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Venue> update(Venue venue) {
        if (venue.getId() != null && jpaVenueRepository.existsById(venue.getId())) {
            VenueEntity entity = VenueEntity.fromDomainModel(venue);
            VenueEntity updatedEntity = jpaVenueRepository.save(entity);
            return Optional.of(updatedEntity.toDomainModel());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (jpaVenueRepository.existsById(id)) {
            jpaVenueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Venue> findAll() {
        return jpaVenueRepository.findAll()
                .stream()
                .map(VenueEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return jpaVenueRepository.findById(id)
                .map(VenueEntity::toDomainModel);
    }
}
