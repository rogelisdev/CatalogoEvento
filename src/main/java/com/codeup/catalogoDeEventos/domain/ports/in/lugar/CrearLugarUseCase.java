package com.codeup.catalogoDeEventos.domain.ports.in.lugar;

import com.codeup.catalogoDeEventos.domain.models.Lugar;

public interface CrearLugarUseCase {
    Lugar crearLugar(Lugar lugar);
}
