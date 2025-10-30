package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Evento;
import com.codeup.catalogoDeEventos.dto.EventoRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {
    private final List<Evento> lista = new ArrayList<>();



    public Evento crear(Evento evento){
        if(evento == null){ throw new IllegalArgumentException("No hay ningun evento disponible");}
        lista.add(evento);
        return evento;
    }

    public List<Evento> listarTodos(){
        return lista;
    }

    public Optional<Evento> buscarID(long id){
        if(id <= 0){throw  new IllegalArgumentException("El id no puede ser igual o menor a 0");}
       return lista.stream()
               .filter(u -> u.getId() == id)
               .findFirst();
    }

    public Evento actualizar(long id, Evento nuevo){
        if(id <= 0){throw  new IllegalArgumentException("El id no puede ser igual o menor a 0");}
        for(Evento e: lista){
            if(e.getId() == id){
                e.setNombre(nuevo.getNombre());
                return e;
            }
        }
        throw new IllegalArgumentException("Evento no encontrado");
    }

    public void eliminar(long id){
        if(id <= 0){throw  new IllegalArgumentException("El id no puede ser igual o menor a 0");}
        lista.removeIf(e -> e.getId() == id);
    }

    public List<Evento> ConseguirIDLugar(long idLugar){
        return lista.stream()
                .filter(u -> u.getIdLugar() == idLugar)
                .toList();
    }

}
