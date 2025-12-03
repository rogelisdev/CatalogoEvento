package com.codeup.catalogoDeEventos.infrastructure.adapters;

import com.codeup.catalogoDeEventos.application.dto.event.EventRequest;
import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;
import com.codeup.catalogoDeEventos.infrastructure.entities.EventEntity;
import com.codeup.catalogoDeEventos.infrastructure.entities.VenueEntity;
import com.codeup.catalogoDeEventos.infrastructure.repositories.JpaEventRepository;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaEventRepositoryAdapter implements EventRepositoryPort {

    private final JpaEventRepository repository;

    public JpaEventRepositoryAdapter(JpaEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event create(@Valid EventRequest event) {
        VenueEntity venueEntity = new VenueEntity(); // You should fetch it from DB
        // Example: venueEntity =
        // venueRepository.findById(event.getVenue().getId()).orElseThrow(...);

        Event domainEvent = new Event(
                null, // ID is null for new events
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getCapacity(),
                event.getPrice());

        EventEntity entity = EventEntity.fromDomainModel(domainEvent, venueEntity);
        EventEntity saved = repository.save(entity);
        return saved.toDomainModel();
    }

    @Override
    public Optional<Event> update(Event event) {
        VenueEntity venueEntity = new VenueEntity(); // You should fetch it from DB
        // Example: venueEntity =
        // venueRepository.findById(event.getVenue().getId()).orElseThrow(...);

        EventEntity entity = EventEntity.fromDomainModel(event, venueEntity);
        EventEntity saved = repository.save(entity); // save acts as insert/update
        return Optional.of(saved.toDomainModel());
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll().stream()
                .map(EventEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Event> findById(Long id) {
        return repository.findById(id)
                .map(EventEntity::toDomainModel);
    }
}
