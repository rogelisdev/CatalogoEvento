package com.codeup.catalogoDeEventos.service.impl;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.EventEntity;
import com.codeup.catalogoDeEventos.domain.VenueEntity;
import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
import com.codeup.catalogoDeEventos.dto.EventRequest;
import com.codeup.catalogoDeEventos.repository.EventRepository;
import com.codeup.catalogoDeEventos.repository.VenueRepository;
import com.codeup.catalogoDeEventos.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;


    @Override
    public List<EventEntity> findAll(Pageable pageable, String city, String category, LocalDateTime startDate) {
        return eventRepository.findAll();
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public EventEntity save(@Valid EventRequest request) {

        // Buscar venue por ID
        VenueEntity venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue", request.getVenueId()));

        // Crear el EventEntity usando builder
        EventEntity event = EventEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .date(request.getDate())
                .capacity(request.getCapacity())
                .price(request.getPrice())
                .venue(venue)
                .build();

        return eventRepository.save(event);
    }


    @Override
    public EventEntity update(Long id, @Valid EventRequest request) {

        EventEntity existing = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));

        // Buscar venue actualizado
        VenueEntity venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue", request.getVenueId()));

        // Actualizar datos
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setDate(request.getDate());
        existing.setCapacity(request.getCapacity());
        existing.setPrice(request.getPrice());
        existing.setVenue(venue);

        return eventRepository.save(existing);
    }


    @Override
    public void delete(Long id) {

        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event", id);
        }

        eventRepository.deleteById(id);
    }

    @Override
    public Optional<DetailsEventResponse> findDetailsById(Long id) {

        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));

        VenueEntity venue = event.getVenue(); // ya viene cargado por JPA

        DetailsEventResponse details = new DetailsEventResponse(event, venue);

        return Optional.of(details);
    }
}
