package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.Event;
import com.codeup.catalogoDeEventos.domain.Venue;
import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
import com.codeup.catalogoDeEventos.dto.EventRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
@AllArgsConstructor
@Service
public class ServiceEvent {
    private final List<Event> array = new ArrayList<>();
    private final ServiceVenue serviceVenue;
    private final AtomicLong counter = new AtomicLong(1);

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

    public List<Event> getAll() {
        return array;
    }

    public Optional<Event> findById(long id) {
        return array.stream().filter(e -> e.getId() == id).findFirst();
    }

    public Optional<Event> updateEvent(long id, EventRequest newEvent) {
        return array.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(e -> {
                    e.setName(newEvent.getName());
                    e.setDescription(newEvent.getDescription());
                    e.setDate(newEvent.getDate());
                    e.setCapacity(newEvent.getCapacity());
                    e.setPrice(newEvent.getPrice());
                    e.setIdVenue(newEvent.getIdVenue());
                    return e;
                });
    }

    public boolean delete(long id) {
        return array.removeIf(e -> e.getId() == id);
    }

    public Optional<DetailsEventResponse> foundDetailsById(long id) {
        // 1. Searches for the Event
        Optional<Event> eventOptional = array.stream()
                .filter(e -> e.getId() == id)
                .findFirst();

        if (eventOptional.isEmpty()) {
            return Optional.empty(); // Event not found
        }

        Event event = eventOptional.get();

        // 2. Uses the event’s venueId to search for the Venue (JOIN)
        // Note: Assumes that VenueService.findById returns Optional<Venue>
        Optional<Venue> VenueOptional = serviceVenue.findById(event.getIdVenue());

        // 3. Checks that the Venue exists
        if (VenueOptional.isEmpty()) {
            // If the venue does not exist, you can throw an exception or simply return the event without venue,
            // but to be strict, we will throw an exception (or a custom 404)
            throw new ResourceNotFoundException("Lugar asociado", event.getIdVenue());
        }

        Venue venue = VenueOptional.get();

        // 4. Maps to the Response DTO
        DetailsEventResponse response = new DetailsEventResponse(event, venue);

        return Optional.of(response);
    }
}