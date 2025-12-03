package com.codeup.catalogoDeEventos.infrastructure.adapters;

import com.codeup.catalogoDeEventos.domain.models.User;
import com.codeup.catalogoDeEventos.domain.ports.out.UserPersistencePort;
import com.codeup.catalogoDeEventos.infrastructure.entities.UserEntity;
import com.codeup.catalogoDeEventos.infrastructure.repositories.JpaUserRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Adaptador para el puerto de persistencia de usuarios.
 * Implementa la interfaz del dominio usando el repositorio JPA.
 * Cumple con el patrón Hexagonal (Ports & Adapters).
 */
@Component
public class UserPersistencePortAdapter implements UserPersistencePort {
    
    private final JpaUserRepository jpaUserRepository;
    
    public UserPersistencePortAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }
    
    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return savedEntity.toDomainModel();
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserEntity::toDomainModel);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(UserEntity::toDomainModel);
    }
}

