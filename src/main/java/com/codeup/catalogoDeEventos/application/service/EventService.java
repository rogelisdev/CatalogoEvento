package com.codeup.catalogoDeEventos.application.service;

import com.codeup.catalogoDeEventos.application.dto.event.EventRequest;
import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.domain.ports.in.event.CreateEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.event.UpdateEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.event.DeleteEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.event.GetEventUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService implements
        CreateEventUseCase,
        UpdateEventUseCase,
        DeleteEventUseCase,
        GetEventUseCase {

    private final CreateEventUseCase createEventUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final GetEventUseCase getEventUseCase;

    public EventService(
            CreateEventUseCase createEventUseCase,
            UpdateEventUseCase updateEventUseCase,
            DeleteEventUseCase deleteEventUseCase,
            GetEventUseCase getEventUseCase
    ) {
        this.createEventUseCase = createEventUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.getEventUseCase = getEventUseCase;
    }



    @Override
    public Event create(@Valid EventRequest event) {
        return createEventUseCase.create(event);
    }

    @Override
    public boolean delete(Long id) {
        deleteEventUseCase.delete(id);
        return false;
    }

    @Override
    public Optional<Event> getById(Long id) {
        return getEventUseCase.getById(id);
    }

    @Override
    public List<Event> getAll() {
        return getEventUseCase.getAll();
    }

    @Override
    public Optional<Event> update(Long id, Event event) {
        return updateEventUseCase.update(id, event);
    }
}
