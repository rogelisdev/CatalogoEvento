package com.codeup.catalogoDeEventos.repository;

import com.codeup.catalogoDeEventos.domain.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByName(String name);
}
