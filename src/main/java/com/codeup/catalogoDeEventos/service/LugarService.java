package com.codeup.catalogoDeEventos.service;

import com.codeup.catalogoDeEventos.domain.Lugar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LugarService {
    private final List<Lugar> lista = new ArrayList<>();

    public Lugar agregar(Lugar lugar){
        if(lugar == null){throw new IllegalArgumentException("La lista esta vacia");}
        lista.add(lugar);
        return lugar;
    }

    public List<Lugar> listarTodos(){
        return lista;
    }

    public Lugar buscarPorID(long id){
        if(id <= 0){throw  new IllegalArgumentException("El id no puede ser igual o menor a 0");}
        return  lista.stream()
                .filter(u->u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id no encontrado"));
    }

    public Lugar actualizar(long id, Lugar nuevo){
        if(id <= 0){throw  new IllegalArgumentException("El id no puede ser igual o menor a 0");}
        for( Lugar l: lista){
            if(l.getId() == id){
                l.setNombre(nuevo.getNombre());
                return l;
            }
        }
        throw new IllegalArgumentException("No se pudo actualizar el lugar");
    }

    public void delete(long id){
        if(id <= 0){throw  new IllegalArgumentException("El id no puede ser igual o menor a 0");}
        lista.removeIf(u -> u.getId() == id);
    }
}
