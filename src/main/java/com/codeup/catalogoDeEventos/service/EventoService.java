package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.EventoEntity;
import com.codeup.catalogoDeEventos.domain.LugarEntity;
import com.codeup.catalogoDeEventos.dto.EventoDetalleResponse;
import com.codeup.catalogoDeEventos.dto.EventoRequest;
import com.codeup.catalogoDeEventos.repository.EventoRepository;
import com.codeup.catalogoDeEventos.repository.LugarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventRepository;
    private final LugarRepository venueRepository;

    public Page<EventoEntity> listarEventos(String ciudad, String categoria, String fechaInicio, Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Optional<EventoEntity> buscarPorId(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional
    public EventoEntity crear(EventoRequest request) {
        if (eventRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un evento con ese nombre");
        }

        LugarEntity lugar = venueRepository.findById(request.getIdLugar())
                .orElseThrow(() -> new ResourceNotFoundException("Lugar", request.getIdLugar()));

        EventoEntity nuevo = EventoEntity.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fecha(request.getFecha())
                .capacidad(request.getCapacidad())
                .precio(request.getPrecio())
                .lugar(lugar)
                .build();

        return eventRepository.save(nuevo);
    }

    @Transactional
    public Optional<EventoEntity> actualizar(Long id, EventoRequest request) {
        return eventRepository.findById(id).map(evento -> {
            evento.setNombre(request.getNombre());
            evento.setDescripcion(request.getDescripcion());
            evento.setFecha(request.getFecha());
            evento.setCapacidad(request.getCapacidad());
            evento.setPrecio(request.getPrecio());
            LugarEntity lugar = venueRepository.findById(request.getIdLugar())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar", request.getIdLugar()));
            evento.setLugar(lugar);
            return eventRepository.save(evento);
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        return eventRepository.findById(id).map(evento -> {
            eventRepository.delete(evento);
            return true;
        }).orElse(false);
    }

    public Optional<EventoDetalleResponse> buscarDetallePorId(Long id) {
        return eventRepository.findById(id)
                .map(evento -> {
                    // Si el evento no tiene un lugar asociado (raro, pero puede pasar si el campo es nullable)
                    if (evento.getLugar() == null) {
                        throw new ResourceNotFoundException("Lugar asociado al evento con ID " + id + " no encontrado");
                    }

                    // Construimos el DTO de respuesta
                    return EventoDetalleResponse.builder()
                            .id(evento.getId())
                            .nombre(evento.getNombre())
                            .descripcion(evento.getDescripcion())
                            .fecha(evento.getFecha())
                            .capacidad(evento.getCapacidad())
                            .precio(evento.getPrecio())
                            .lugar(evento.getLugar()) // devuelve el objeto completo de VenueEntity
                            .build();
                });
    }
}
