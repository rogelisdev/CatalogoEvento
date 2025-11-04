package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Evento;
import com.codeup.catalogoDeEventos.domain.Lugar;
import com.codeup.catalogoDeEventos.dto.EventoDetalleResponse;
import com.codeup.catalogoDeEventos.dto.EventoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
@AllArgsConstructor
@Service
public class EventoService {
    private final List<Evento> lista = new ArrayList<>();
    private final LugarService lugarService;
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

    public Optional<EventoDetalleResponse> buscarDetallePorId(long id) {
        // 1. Busca el Evento
        Optional<Evento> eventoOpt = lista.stream()
                .filter(e -> e.getId() == id)
                .findFirst();

        if (eventoOpt.isEmpty()) {
            return Optional.empty(); // Evento no encontrado
        }

        Evento evento = eventoOpt.get();

        // 2. Usa el idLugar del Evento para buscar el Lugar (JOIN)
        // Nota: Asume que LugarService.buscarPorID devuelve Optional<Lugar> (como corregimos)
        Optional<Lugar> lugarOpt = lugarService.buscarPorID(evento.getIdLugar());

        // 3. Verifica que el Lugar exista
        if (lugarOpt.isEmpty()) {
            // Si el lugar no existe, puedes lanzar una excepción o simplemente devolver el evento sin lugar,
            // pero para ser estricto, lanzaremos una excepción (o un 404 personalizado)
            throw new ResourceNotFoundException("Lugar asociado", evento.getIdLugar());
        }

        Lugar lugar = lugarOpt.get();

        // 4. Mapea al DTO de Respuesta
        EventoDetalleResponse response = new EventoDetalleResponse(evento, lugar);

        return Optional.of(response);
    }
}