package com.codeup.catalogoDeEventos.service.impl;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Event;
import com.codeup.catalogoDeEventos.domain.Venue;
import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
import com.codeup.catalogoDeEventos.dto.EventRequest;
import com.codeup.catalogoDeEventos.service.EventService;
import com.codeup.catalogoDeEventos.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final List<Event> array = new ArrayList<>();
    private final VenueService venueService;
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public Event create(EventRequest request) {
        Event event = new Event();
        event.setId(counter.getAndIncrement());
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setDate(request.getDate());
        event.setCapacity(request.getCapacity());
        event.setIdVenue(request.getIdVenue());
        event.setPrice(request.getPrice());

        array.add(event);
        return event;
    }

    @Override
    public List<Event> getAll() {
        return array;
    }

    @Override
    public Optional<Event> findById(long id) {
        return array.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Event> update(long id, EventRequest update) {
        return findById(id).map(event -> {
            event.setName(update.getName());
            event.setDescription(update.getDescription());
            event.setDate(update.getDate());
            event.setCapacity(update.getCapacity());
            event.setIdVenue(update.getIdVenue());
            event.setPrice(update.getPrice());
            return event;
        });
    }

    @Override
    public boolean delete(long id) {
        return array.removeIf(e -> e.getId() == id);
    }

    @Override
    public Optional<DetailsEventResponse> findDetailsById(long id) {

        Event event = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));

        Venue venue = venueService.findById(event.getIdVenue())
                .orElseThrow(() -> new ResourceNotFoundException("Venue", event.getIdVenue()));

        return Optional.of(new DetailsEventResponse(event, venue));
    }
}
