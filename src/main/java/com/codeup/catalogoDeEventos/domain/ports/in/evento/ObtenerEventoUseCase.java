package com.codeup.catalogoDeEventos.domain.ports.in.evento;

import com.codeup.catalogoDeEventos.domain.models.Evento;

import java.util.List;
import java.util.Optional;

public interface ObtenerEventoUseCase {
    Optional<Evento> obtenerEventoPorId(Long id);
    List<Evento> obtenerEventos();
}
