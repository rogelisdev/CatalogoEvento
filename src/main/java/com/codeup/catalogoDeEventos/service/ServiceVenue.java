package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Venue;
import com.codeup.catalogoDeEventos.dto.VenueRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ServiceVenue {
    private final List<Venue> arrayList = new ArrayList<>();
    // 1. Add the counter for unique IDs
    private final AtomicLong counter = new AtomicLong(1);

    public Venue create(VenueRequest request) {
        if(request == null){
            throw new IllegalArgumentException("The request must not be null.");
        }

        Venue venue = new Venue();
        venue.setId(counter.getAndIncrement()); // Assign unique ID
        venue.setName(request.getName());
        venue.setCapacity(request.getCapacity());
        venue.setAddress(request.getAddress());
        venue.setCountry(request.getCountry());
        venue.setCity(request.getCity());

        arrayList.add(venue);
        return venue;
    }

    public List<Venue> getAll(){
        return arrayList;
    }

    // 3. Return Optional to handle 404 in the Controller
    public Optional<Venue> findById(long id){
        // Validation for id <= 0 can be moved to the DTO or kept here as a pre-condition
        if(id <= 0){throw new IllegalArgumentException("The ID must be positive");}

        return arrayList.stream()
                .filter(l -> l.getId() == id)
                .findFirst(); // If not found, returns Optional.empty()
    }

    // 4. Use DTO as input and return Optional
    public Optional<Venue> updateVenue(long id, VenueRequest newVenue) {
        if(id <= 0){throw new IllegalArgumentException("The ID must be positive");}

        return arrayList.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .map(l -> {
                    // Map the DTO to the existing object
                    l.setName(newVenue.getName());
                    l.setCapacity(newVenue.getCapacity());
                    l.setAddress(newVenue.getAddress());
                    l.setCountry(newVenue.getCountry());
                    l.setCity(newVenue.getCity());
                    return l;
                });
    }

    public boolean delete(long id){
        if(id <= 0){throw new IllegalArgumentException("The ID must be positive");}
        return arrayList.removeIf(l -> l.getId() == id);
    }
}

