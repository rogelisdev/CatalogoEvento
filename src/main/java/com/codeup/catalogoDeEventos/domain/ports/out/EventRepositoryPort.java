package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.domain.models.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {

    Event create(Event event);

    Optional<Event> update(Event event);

    boolean delete(Long id);

    List<Event> findAll();

    Optional<Event> findById(Long id);
}
