package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Lugar;
import com.codeup.catalogoDeEventos.dto.LugarRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LugarService {
    private final List<Lugar> lista = new ArrayList<>();
    // 1. Añadir el contador para IDs únicos
    private final AtomicLong contador = new AtomicLong(1);


    public Lugar crear(LugarRequest request) {
        if(request == null){
            throw new IllegalArgumentException("La solicitud no debe ser nula.");
        }

        Lugar lugar = new Lugar();
        lugar.setId(contador.getAndIncrement()); // Asignar ID único
        lugar.setNombre(request.getNombre());
        lugar.setCapacidad(request.getCapacidad());
        lugar.setDireccion(request.getDireccion());
        lugar.setPais(request.getPais());
        lugar.setCiudad(request.getCiudad());

        lista.add(lugar);
        return lugar;
    }

    public List<Lugar> listarTodos(){
        return lista;
    }

    // 3. Devolver Optional para manejar el 404 en el Controller
    public Optional<Lugar> buscarPorID(long id){
        // La validación de id <= 0 se puede mover al DTO o mantener aquí como pre-condición
        if(id <= 0){throw  new IllegalArgumentException("El ID debe ser positivo");}

        return  lista.stream()
                .filter(l -> l.getId() == id)
                .findFirst(); // Si no se encuentra, devuelve Optional.empty()
    }

    // 4. Usar DTO en la entrada y devolver Optional
    public Optional<Lugar> actualizar(long id, LugarRequest nuevo) {
        if(id <= 0){throw  new IllegalArgumentException("El ID debe ser positivo");}

        return lista.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .map(l -> {
                    // Mapear el DTO al objeto existente
                    l.setNombre(nuevo.getNombre());
                    l.setCapacidad(nuevo.getCapacidad());
                    l.setDireccion(nuevo.getDireccion());
                    l.setPais(nuevo.getPais());
                    l.setCiudad(nuevo.getCiudad());
                    return l;
                });
    }


    public boolean eliminar(long id){
        if(id <= 0){throw  new IllegalArgumentException("El ID debe ser positivo");}
        return lista.removeIf(l -> l.getId() == id);
    }
}
