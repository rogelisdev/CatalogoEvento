package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.domain.models.Lugar;

import java.util.List;
import java.util.Optional;

public interface LugarRepositoryPort {
    Lugar crear(Lugar lugar);
    Optional<Lugar> actualizar(Lugar lugar);
    boolean eliminar(Long id);
    List<Lugar> obtenerLugares();
    Optional<Lugar> obtenerPorId(Long id);
}
