package com.codeup.catalogoDeEventos.domain.ports.in.event;

import com.codeup.catalogoDeEventos.domain.models.Event;

import java.util.List;
import java.util.Optional;

public interface GetEventUseCase {
    Optional<Event> getById(Long id);
    List<Event> getAll();
}
