package com.codeup.catalogoDeEventos.application.usecase.evento;

import com.codeup.catalogoDeEventos.domain.models.Evento;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.ObtenerEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.EventoRepositoryPort;

import java.util.List;
import java.util.Optional;

public class ObtenerEventoUseCaseImpl implements ObtenerEventoUseCase {

    private final EventoRepositoryPort port;

    public ObtenerEventoUseCaseImpl(EventoRepositoryPort port) {
        this.port = port;
    }

    @Override
    public Optional<Evento> obtenerEventoPorId(Long id) {
        return port.obtenerPorId(id);
    }

    @Override
    public List<Evento> obtenerEventos() {
        return port.obtenerEventos();
    }
}
