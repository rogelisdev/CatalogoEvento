package com.codeup.catalogoDeEventos.application.service;

import com.codeup.catalogoDeEventos.domain.models.Lugar;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.ActualizarLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.CrearLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.EliminarLugarUseCase;
import com.codeup.catalogoDeEventos.domain.ports.in.lugar.ObtenerLugarUseCase;
import com.codeup.catalogoDeEventos.infrastructure.entities.LugarEntity;
import com.codeup.catalogoDeEventos.application.dto.lugar.LugarRequest;
import com.codeup.catalogoDeEventos.infrastructure.repositories.LugarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LugarService implements CrearLugarUseCase, ActualizarLugarUseCase, EliminarLugarUseCase, ObtenerLugarUseCase {

    private final CrearLugarUseCase crear;
    private final ActualizarLugarUseCase actualizar;
    private final EliminarLugarUseCase eliminar;
    private final ObtenerLugarUseCase obtener;

    public LugarService(CrearLugarUseCase crear, ActualizarLugarUseCase actualizar, EliminarLugarUseCase eliminar, ObtenerLugarUseCase obtener) {
        this.crear = crear;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
        this.obtener = obtener;
    }

    @Override
    public Optional<Lugar> actualizarLugar(Long id, Lugar lugar) {
        return actualizar.actualizarLugar(id,lugar);
    }

    @Override
    public Lugar crearLugar(Lugar lugar) {
        return crear.crearLugar(lugar);
    }

    @Override
    public boolean eliminarLugar(Long id) {
        return eliminar.eliminarLugar(id);
    }

    @Override
    public Optional<Lugar> ObtenerLugarPorId(Long id) {
        return obtener.ObtenerLugarPorId(id);
    }

    @Override
    public List<Lugar> ObtenerLugares() {
        return obtener.ObtenerLugares();
    }
}
    /*private final LugarRepository repository;

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
} */
