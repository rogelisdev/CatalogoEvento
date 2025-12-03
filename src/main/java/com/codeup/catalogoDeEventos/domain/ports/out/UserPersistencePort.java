package com.codeup.catalogoDeEventos.domain.ports.out;

import com.codeup.catalogoDeEventos.domain.models.User;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de usuarios.
 * Interfaz que define el contrato para la persistencia de usuarios.
 * Cumple con principio de Inversión de Dependencias (DIP).
 */
public interface UserPersistencePort {
    
    /**
     * Guarda un usuario en el repositorio
     * @param user usuario a guardar
     * @return usuario guardado
     */
    User save(User user);
    
    /**
     * Busca un usuario por email
     * @param email email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Busca un usuario por ID
     * @param id id del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findById(Long id);
}
