package com.codeup.catalogoDeEventos.application.usecase.evento;

import com.codeup.catalogoDeEventos.domain.models.Evento;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.ActualizarEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventoRepositoryPort;

import java.util.Optional;

public class ActualizarEventoUseCaseImpl implements ActualizarEventoUseCase {

    private final EventoRepositoryPort port;

    public ActualizarEventoUseCaseImpl(EventoRepositoryPort port) {
        this.port = port;
    }

    @Override
    public Optional<Evento> actualizarEvento(Long id, Evento evento) {
        return port.actualizar(evento);
    }
}
