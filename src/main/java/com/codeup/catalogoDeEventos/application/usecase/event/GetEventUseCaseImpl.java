package com.codeup.catalogoDeEventos.application.usecase.event;

import com.codeup.catalogoDeEventos.domain.models.Event;
import com.codeup.catalogoDeEventos.domain.ports.in.event.GetEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GetEventUseCaseImpl implements GetEventUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetEventUseCaseImpl.class);

    private final EventRepositoryPort eventRepository;

    public GetEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Event> getById(Long id) {
        logger.debug("Fetching event with ID: {}", id);
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getAll() {
        logger.debug("Fetching all events");
        List<Event> events = eventRepository.findAll();
        logger.info("Retrieved {} events", events.size());
        return events;
    }
}
