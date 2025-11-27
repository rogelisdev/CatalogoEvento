package com.codeup.catalogoDeEventos.infrastructure.adapters;

import com.codeup.catalogoDeEventos.domain.models.Evento;
import com.codeup.catalogoDeEventos.domain.ports.out.EventoRepositoryPort;
import com.codeup.catalogoDeEventos.infrastructure.entities.EventoEntity;
import com.codeup.catalogoDeEventos.infrastructure.repositories.JpaEventoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaEventoRepositoryAdapter implements EventoRepositoryPort {

    private final JpaEventoRepository jpaEventoRepository;

    public JpaEventoRepositoryAdapter(JpaEventoRepository jpaEventoRepository) {
        this.jpaEventoRepository = jpaEventoRepository;
    }

    @Override
    public Evento crear(Evento evento) {
        EventoEntity entity = EventoEntity.fromDomainModel(evento);
        EventoEntity saveEntity = jpaEventoRepository.save(entity);
        return saveEntity.toDomainModel();
    }

    @Override
    public Optional<Evento> actualizar(Evento evento) {
        if(jpaEventoRepository.existsById(evento.getId())){
            EventoEntity entity = EventoEntity.fromDomainModel(evento);
            EventoEntity updateEntity = jpaEventoRepository.save(entity);
            return Optional.of(updateEntity.toDomainModel());
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long id) {
        if(jpaEventoRepository.existsById(id)){
            jpaEventoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Evento> obtenerEventos() {
        return jpaEventoRepository.findAll().stream()
                .map(EventoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Evento> obtenerPorId(Long id) {
        return jpaEventoRepository.findById(id).map(EventoEntity::toDomainModel);
    }
}
