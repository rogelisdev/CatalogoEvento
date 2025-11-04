package com.codeup.catalogoDeEventos.repository;

import com.codeup.catalogoDeEventos.domain.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long>, JpaSpecificationExecutor<EventoEntity> {
    Optional<EventoEntity> findByNombre(String nombre);
}
