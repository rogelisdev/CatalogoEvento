package com.codeup.catalogoDeEventos.application.usecase.event;

import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.domain.ports.in.event.GetEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;

import java.util.List;
import java.util.Optional;

public class GetEventUseCaseImpl implements GetEventUseCase {

    private final EventRepositoryPort eventRepository;

    public GetEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
