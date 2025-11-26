package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.domain.models.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepositoryPort {
    Evento crear (Evento evento);
    Optional<Evento> actualizar(Evento evento);
    boolean eliminar(Long id);
    List<Evento> obtenerEventos();
    Optional<Evento> obtenerPorId(Long id);


}
