package com.codeup.catalogoDeEventos.application.usecase.lugar;

import com.codeup.catalogoDeEventos.domain.models.Lugar;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.ObtenerLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.LugarRepositoryPort;

import java.util.List;
import java.util.Optional;

public class ObtenerLugarUseCaseImpl implements ObtenerLugarUseCase {

    private final LugarRepositoryPort port;

    public ObtenerLugarUseCaseImpl(LugarRepositoryPort port) {
        this.port = port;
    }


    @Override
    public Optional<Lugar> ObtenerLugarPorId(Long id) {
        return port.obtenerPorId(id);
    }

    @Override
    public List<Lugar> ObtenerLugares() {
        return port.obtenerLugares();
    }
}
