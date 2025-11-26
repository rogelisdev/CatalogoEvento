package com.codeup.catalogoDeEventos.application.usecase.lugar;

import com.codeup.catalogoDeEventos.domain.models.Lugar;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.CrearLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.out.LugarRepositoryPort;

public class CrearLugarUseCaseImpl implements CrearLugarUseCase {

    private final LugarRepositoryPort port;

    public CrearLugarUseCaseImpl(LugarRepositoryPort port) {
        this.port = port;
    }

    @Override
    public Lugar crearLugar(Lugar lugar) {
        return port.crear(lugar);
    }
}
