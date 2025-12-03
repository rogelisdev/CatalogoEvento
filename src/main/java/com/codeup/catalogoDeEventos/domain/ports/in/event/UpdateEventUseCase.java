package com.codeup.catalogoDeEventos.domain.ports.in.event;

import com.codeup.catalogoDeEventos.domain.models.Event;

import java.util.Optional;

public interface UpdateEventUseCase {
    Optional<Event> update(Long id, Event event);
}
