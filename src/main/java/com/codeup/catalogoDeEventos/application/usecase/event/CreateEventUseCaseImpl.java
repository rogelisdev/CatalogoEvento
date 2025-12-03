package com.codeup.catalogoDeEventos.application.usecase.event;

import com.codeup.catalogoDeEventos.application.dto.event.EventRequest;
import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.domain.ports.in.event.CreateEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepositoryPort eventRepository;

    public CreateEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event create(@Valid EventRequest event) {
        return eventRepository.create(event);
    }
}
