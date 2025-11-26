package com.codeup.catalogoDeEventos.application.usecase.lugar;

import com.codeup.catalogoDeEventos.domain.models.Lugar;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.ActualizarLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.LugarRepositoryPort;

import java.util.Optional;

public class ActualizarLugarUseCaseImpl implements ActualizarLugarUseCase {

    private final LugarRepositoryPort port;

    public ActualizarLugarUseCaseImpl(LugarRepositoryPort port) {
        this.port = port;
    }


    @Override
    public Optional<Lugar> actualizarLugar(Long id, Lugar lugar) {
        return port.actualizar(lugar);
    }
}
