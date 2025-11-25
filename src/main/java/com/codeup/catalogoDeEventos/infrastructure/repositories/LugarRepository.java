package com.codeup.catalogoDeEventos.infrastructure.repositories;

import com.codeup.catalogoDeEventos.infrastructure.entities.LugarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarRepository extends JpaRepository<LugarEntity, Long> {
    boolean existsByNombre(String nombre);
}
