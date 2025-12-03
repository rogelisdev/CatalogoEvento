package com.codeup.catalogoDeEventos.infrastructure.repositories;

import com.codeup.catalogoDeEventos.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    @EntityGraph(attributePaths = { "tasks" })
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findByIdWithTasks(@Param("id") Long id);

    @EntityGraph(attributePaths = { "tasks", "events" })
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findByIdWithTasksAndEvents(@Param("id") Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = { "tasks" })
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllWithTasks();
}
