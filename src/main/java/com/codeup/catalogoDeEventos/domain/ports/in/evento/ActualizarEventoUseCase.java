package com.codeup.catalogoDeEventos.domain.ports.in.evento;

import com.codeup.catalogoDeEventos.domain.models.Evento;

import java.util.Optional;

public interface ActualizarEventoUseCase {
    Optional<Evento> actualizarEvento(Long id, Evento evento);
}
