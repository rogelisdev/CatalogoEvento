package com.codeup.catalogoDeEventos.application.usecase.evento;

import com.codeup.catalogoDeEventos.domain.models.Evento;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.CrearEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventoRepositoryPort;

public class CrearEventoUseCaseImpl implements CrearEventoUseCase {

    private final EventoRepositoryPort port;

    public CrearEventoUseCaseImpl(EventoRepositoryPort port) {
        this.port = port;
    }

    @Override
    public Evento crearEvento(Evento evento) {
        return port.crear(evento);
    }
}
