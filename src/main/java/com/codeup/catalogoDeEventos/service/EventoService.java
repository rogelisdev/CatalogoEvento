package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Evento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventoService {
    private final List<Evento> lista = new ArrayList<>();
    private final Evento evento;

    public Evento crear(Evento evento){
        lista.add(evento);
        return evento;
    }

    public void listarTodos(){

    }
}
