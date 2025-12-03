package com.codeup.catalogoDeEventos.service.impl;

import com.codeup.catalogoDeEventos.advice.ResourceNotFoundException;
import com.codeup.catalogoDeEventos.domain.VenueEntity;
import com.codeup.catalogoDeEventos.dto.VenueRequest;
import com.codeup.catalogoDeEventos.repository.VenueRepository;
import com.codeup.catalogoDeEventos.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    @Transactional
    public VenueEntity create(VenueRequest request) {
        log.debug("Creating venue: {}", request.getName());

        // Check if venue name already exists
        if (venueRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Venue with name '" + request.getName() + "' already exists");
        }

        VenueEntity venue = VenueEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .capacity(request.getCapacity())
                .build();

        VenueEntity saved = venueRepository.save(venue);
        log.info("Venue created with ID: {}", saved.getId());

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VenueEntity> findAll(Pageable pageable) {
        log.debug("Finding all venues with pagination");
        return venueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VenueEntity> findById(Long id) {
        log.debug("Finding venue by ID: {}", id);
        return venueRepository.findById(id);
    }

    @Override
    @Transactional
    public VenueEntity update(Long id, VenueRequest request) {
        log.debug("Updating venue with ID: {}", id);

        VenueEntity existing = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));

        // Check if new name conflicts with another venue
        if (!existing.getName().equals(request.getName()) &&
                venueRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Venue with name '" + request.getName() + "' already exists");
        }

        existing.setName(request.getName());
        existing.setAddress(request.getAddress());
        existing.setCity(request.getCity());
        existing.setCountry(request.getCountry());
        existing.setCapacity(request.getCapacity());

        VenueEntity updated = venueRepository.save(existing);
        log.info("Venue updated with ID: {}", id);

        return updated;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting venue with ID: {}", id);

        if (!venueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venue", id);
        }

        // Check if venue has associated events
        VenueEntity venue = venueRepository.findById(id).get();
        if (venue.getEvents() != null && !venue.getEvents().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete venue with ID " + id + " because it has " +
                            venue.getEvents().size() + " associated event(s)"
            );
        }

        venueRepository.deleteById(id);
        log.info("Venue deleted with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VenueEntity> findByCity(String city) {
        log.debug("Finding venues by city: {}", city);
        return venueRepository.findAll().stream()
                .filter(v -> v.getCity().equalsIgnoreCase(city))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VenueEntity> findByMinimumCapacity(Integer minCapacity) {
        log.debug("Finding venues with minimum capacity: {}", minCapacity);
        return venueRepository.findAll().stream()
                .filter(v -> v.getCapacity() >= minCapacity)
                .toList();
    }
}