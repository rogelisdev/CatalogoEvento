package com.codeup.catalogoDeEventos.application.usecase.event;

import com.codeup.catalogoDeEventos.domain.ports.in.event.DeleteEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {

    private static final Logger logger = LoggerFactory.getLogger(DeleteEventUseCaseImpl.class);

    private final EventRepositoryPort eventRepository;

    public DeleteEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Deleting event with ID: {}", id);
        eventRepository.delete(id);
        logger.info("Event deleted successfully: {}", id);
        return true;
    }
}
