package com.codeup.catalogoDeEventos.application.usecase.event;

import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.domain.ports.in.event.UpdateEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;

import java.util.Optional;

public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventRepositoryPort eventRepository;

    public UpdateEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Event> update(Long id, Event updatedEvent) {
        return eventRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(updatedEvent.getName());
                    existing.setDescription(updatedEvent.getDescription());
                    existing.setDate(updatedEvent.getDate());
                    existing.setCapacity(updatedEvent.getCapacity());
                    existing.setPrice(updatedEvent.getPrice());

                    return eventRepository.update(existing);
                });
    }
}
