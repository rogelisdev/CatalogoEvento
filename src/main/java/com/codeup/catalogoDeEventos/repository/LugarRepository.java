package com.codeup.catalogoDeEventos.repository;

import com.codeup.catalogoDeEventos.domain.LugarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarRepository extends JpaRepository<LugarEntity, Long> {
    boolean existsByNombre(String nombre);
}
