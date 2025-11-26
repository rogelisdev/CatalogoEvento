package com.codeup.catalogoDeEventos.application.service;

import com.codeup.catalogoDeEventos.domain.models.Evento;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.ActualizarEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.CrearEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.EliminarEventoUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.evento.ObtenerEventoUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService implements CrearEventoUseCase, ActualizarEventoUseCase, EliminarEventoUseCase, ObtenerEventoUseCase {

    private final CrearEventoUseCase crear;
    private final ActualizarEventoUseCase actualizar;
    private final EliminarEventoUseCase eliminar;
    private final ObtenerEventoUseCase obtener;

    public EventoService(CrearEventoUseCase crear, ActualizarEventoUseCase actualizar, EliminarEventoUseCase eliminar, ObtenerEventoUseCase obtener) {
        this.crear = crear;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
        this.obtener = obtener;
    }

    @Override
    public Optional<Evento> actualizarEvento(Long id, Evento evento) {
        return actualizar.actualizarEvento(id, evento);
    }

    @Override
    public Evento crearEvento(Evento evento) {
        return crear.crearEvento(evento);
    }

    @Override
    public boolean eliminarEvento(Long id) {
        return eliminar.eliminarEvento(id);
    }

    @Override
    public Optional<Evento> obtenerEventoPorId(Long id) {
        return obtener.obtenerEventoPorId(id);
    }

    @Override
    public List<Evento> obtenerEventos() {
        return obtener.obtenerEventos();
    }
}
    /*private final EventoRepository eventRepository;
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
} */

