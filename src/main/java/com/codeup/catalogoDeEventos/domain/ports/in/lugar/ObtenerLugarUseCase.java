package com.codeup.catalogoDeEventos.domain.ports.in.lugar;

import com.codeup.catalogoDeEventos.domain.models.Lugar;

import java.util.List;
import java.util.Optional;

public interface ObtenerLugarUseCase {
    Optional<Lugar> ObtenerLugarPorId(Long id);
    List<Lugar> ObtenerLugares();
}
