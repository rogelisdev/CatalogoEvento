package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.EventEntity;
import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
import com.codeup.catalogoDeEventos.dto.EventRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    List<EventEntity> findAll(Pageable pageable, String city, String category, LocalDateTime startDate);

    Optional<EventEntity> findById(Long id);

    EventEntity save(@Valid EventRequest event);

    EventEntity update(Long id, @Valid EventRequest event);

    void delete(Long id);

    Optional<DetailsEventResponse> findDetailsById(Long id);
}
