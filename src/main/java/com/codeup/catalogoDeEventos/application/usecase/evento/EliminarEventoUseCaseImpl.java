package com.codeup.catalogoDeEventos.application.usecase.evento;

import com.codeup.catalogoDeEventos.domain.ports.in.evento.EliminarEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventoRepositoryPort;

public class EliminarEventoUseCaseImpl implements EliminarEventoUseCase {

    private final EventoRepositoryPort port;

    public EliminarEventoUseCaseImpl(EventoRepositoryPort port) {
        this.port = port;
    }


    @Override
    public boolean eliminarEvento(Long id) {
        return port.eliminar(id);
    }
}
