package com.codeup.catalogoDeEventos.domain.ports.in.evento;

import com.codeup.catalogoDeEventos.domain.models.Evento;

public interface CrearEventoUseCase {
    Evento crearEvento(Evento evento);
}
