package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.VenueEntity;
import com.codeup.catalogoDeEventos.dto.VenueRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VenueService {

    VenueEntity create(VenueRequest request);

    Page<VenueEntity> findAll(Pageable pageable);

    Optional<VenueEntity> findById(Long id);

    VenueEntity update(Long id, VenueRequest request);

    void delete(Long id);

    List<VenueEntity> findByCity(String city);

    List<VenueEntity> findByMinimumCapacity(Integer minCapacity);
}