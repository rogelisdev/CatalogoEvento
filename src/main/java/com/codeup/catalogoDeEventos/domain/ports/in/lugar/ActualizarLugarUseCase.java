package com.codeup.catalogoDeEventos.domain.ports.in.lugar;

import com.codeup.catalogoDeEventos.domain.models.Lugar;

import java.util.Optional;

public interface ActualizarLugarUseCase {
    Optional<Lugar> actualizarLugar(Long id, Lugar lugar);
}
