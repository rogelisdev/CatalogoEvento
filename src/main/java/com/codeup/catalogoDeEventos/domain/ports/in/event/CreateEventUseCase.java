package com.codeup.catalogoDeEventos.domain.ports.in.event;

import com.codeup.catalogoDeEventos.application.dto.event.EventRequest;
import com.codeup.catalogoDeEventos.domain.models.Event;
import jakarta.validation.Valid;

public interface CreateEventUseCase {
    Event create(@Valid EventRequest event);
}
