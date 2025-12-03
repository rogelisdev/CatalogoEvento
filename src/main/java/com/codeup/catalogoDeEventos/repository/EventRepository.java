package com.codeup.catalogoDeEventos.repository;

import com.codeup.catalogoDeEventos.domain.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {
    Optional<EventEntity> findByName(String name);
}
