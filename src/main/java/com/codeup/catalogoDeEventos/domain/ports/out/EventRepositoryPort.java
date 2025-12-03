package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.application.dto.event.EventRequest;
import com.codeup.catalogoDeEventos.domain.models.Event;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {

    Event create(@Valid EventRequest event);

    Optional<Event> update(Event event);

    boolean delete(Long id);

    List<Event> findAll();

    Optional<Event> findById(Long id);
}
