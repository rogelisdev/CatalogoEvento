package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Venue;
import com.codeup.catalogoDeEventos.dto.VenueRequest;

import java.util.List;
import java.util.Optional;


public interface VenueService {

    Venue create(VenueRequest request);
    List<Venue> getAll();
    Optional<Venue> findById(long id);
    Optional<Venue> update(long id, VenueRequest request);
    boolean delete(long id);
}

