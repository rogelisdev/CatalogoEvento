package com.codeup.catalogoDeEventos.application.usecase.lugar;

import com.codeup.catalogoDeEventos.domain.ports.in.lugar.EliminarLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.LugarRepositoryPort;

public class EliminarLugarUseCaseImpl implements EliminarLugarUseCase {

    private final LugarRepositoryPort port;

    public EliminarLugarUseCaseImpl(LugarRepositoryPort port) {
        this.port = port;
    }


    @Override
    public boolean eliminarLugar(Long id) {
        return port.eliminar(id);
    }
}
