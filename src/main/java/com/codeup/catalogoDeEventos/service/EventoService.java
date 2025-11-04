package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Evento;
import com.codeup.catalogoDeEventos.dto.EventoRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventoService {
    private final List<Evento> lista = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public Evento crear(EventoRequest request) {
        Evento evento = new Evento();
        evento.setId(contador.getAndIncrement());
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFecha(request.getFecha());
        evento.setCapacidad(request.getCapacidad());
        evento.setIdLugar(request.getIdLugar());
        evento.setPrecio(request.getPrecio());
        lista.add(evento);
        return evento;
    }

    public List<Evento> listarTodos() {
        return lista;
    }

    public Optional<Evento> buscarPorId(long id) {
        return lista.stream().filter(e -> e.getId() == id).findFirst();
    }

    public Optional<Evento> actualizar(long id, EventoRequest nuevo) {
        return lista.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(e -> {
                    e.setNombre(nuevo.getNombre());
                    e.setDescripcion(nuevo.getDescripcion());
                    e.setFecha(nuevo.getFecha());
                    e.setCapacidad(nuevo.getCapacidad());
                    e.setPrecio(nuevo.getPrecio());
                    e.setIdLugar(nuevo.getIdLugar());
                    return e;
                });
    }

    public boolean eliminar(long id) {
        return lista.removeIf(e -> e.getId() == id);
    }
}