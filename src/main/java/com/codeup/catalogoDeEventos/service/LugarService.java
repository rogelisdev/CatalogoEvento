package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.LugarEntity;
import com.codeup.catalogoDeEventos.dto.LugarRequest;
import com.codeup.catalogoDeEventos.repository.LugarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LugarService {

    private final LugarRepository repository;

    public LugarEntity crear(LugarRequest request) {
        LugarEntity lugar = LugarEntity.builder()
                .nombre(request.getNombre())
                .capacidad(request.getCapacidad())
                .direccion(request.getDireccion())
                .pais(request.getPais())
                .ciudad(request.getCiudad())
                .build();

        return repository.save(lugar);
    }

    public List<LugarEntity> listarTodos() {
        return repository.findAll();
    }

    public Optional<LugarEntity> buscarPorID(long id) {
        return repository.findById(id);
    }

    public Optional<LugarEntity> actualizar(long id, LugarRequest nuevo) {
        return repository.findById(id).map(lugar -> {
            lugar.setNombre(nuevo.getNombre());
            lugar.setCapacidad(nuevo.getCapacidad());
            lugar.setDireccion(nuevo.getDireccion());
            lugar.setPais(nuevo.getPais());
            lugar.setCiudad(nuevo.getCiudad());
            return repository.save(lugar);
        });
    }

    public boolean eliminar(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
