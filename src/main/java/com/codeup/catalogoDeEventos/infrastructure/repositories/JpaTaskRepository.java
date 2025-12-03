package com.codeup.catalogoDeEventos.infrastructure.repositories;

import com.codeup.catalogoDeEventos.infrastructure.entities.TaskEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {

    @EntityGraph(attributePaths = { "user" })
    @Query("SELECT t FROM TaskEntity t WHERE t.id = :id")
    Optional<TaskEntity> findByIdWithUser(@Param("id") Long id);

    @EntityGraph(attributePaths = { "user" })
    @Query("SELECT t FROM TaskEntity t")
    List<TaskEntity> findAllWithUser();

    @Query("SELECT t FROM TaskEntity t WHERE t.status = :status")
    List<TaskEntity> findByStatus(@Param("status") String status);

    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId")
    List<TaskEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId AND t.status = :status")
    List<TaskEntity> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);
}
