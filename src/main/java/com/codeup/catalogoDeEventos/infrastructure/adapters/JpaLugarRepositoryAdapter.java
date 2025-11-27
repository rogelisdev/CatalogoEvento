package com.codeup.catalogoDeEventos.infrastructure.adapters;

import com.codeup.catalogoDeEventos.domain.models.Lugar;
import com.codeup.catalogoDeEventos.domain.ports.out.LugarRepositoryPort;
import com.codeup.catalogoDeEventos.infrastructure.entities.LugarEntity;
import com.codeup.catalogoDeEventos.infrastructure.repositories.JpaLugarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaLugarRepositoryAdapter implements LugarRepositoryPort {

    private final JpaLugarRepository jpaLugarRepository;

    public JpaLugarRepositoryAdapter(JpaLugarRepository jpaLugarRepository) {
        this.jpaLugarRepository = jpaLugarRepository;
    }

    @Override
    public Lugar crear(Lugar lugar) {
        LugarEntity entity = LugarEntity.fromDomainModel(lugar);
        LugarEntity saveEntity = jpaLugarRepository.save(entity);
        return saveEntity.toDomainModel();
    }

    @Override
    public Optional<Lugar> actualizar(Lugar lugar) {
        if(jpaLugarRepository.existsById(lugar.getId())){
            LugarEntity entity = LugarEntity.fromDomainModel(lugar);
            LugarEntity updateEntity = jpaLugarRepository.save(entity);
            return Optional.of(updateEntity.toDomainModel());
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long id) {
        if(jpaLugarRepository.existsById(id)){
            jpaLugarRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Lugar> obtenerLugares() {
        return jpaLugarRepository.findAll().stream()
                .map(LugarEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Lugar> obtenerPorId(Long id) {
        return jpaLugarRepository.findById(id)
                .map(LugarEntity::toDomainModel);
    }
}
