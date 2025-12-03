package com.codeup.catalogoDeEventos.application.usecase.event;

import com.codeup.catalogoDeEventos.domain.ports.in.event.DeleteEventUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {

    private final EventRepositoryPort eventRepository;

    public DeleteEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean delete(Long id) {
        eventRepository.delete(id);
        return false;
    }
}
