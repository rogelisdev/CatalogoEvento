package com.codeup.catalogoDeEventos.infrastructure.repositories;

import com.codeup.catalogoDeEventos.infrastructure.entities.EventEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaEventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

    @EntityGraph(attributePaths = { "venue" })
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findByIdWithVenue(@Param("id") Long id);

    @EntityGraph(attributePaths = { "venue" })
    @Query("SELECT e FROM EventEntity e")
    List<EventEntity> findAllWithVenue();

    @Query("SELECT e FROM EventEntity e WHERE e.date >= :startDate AND e.date <= :endDate")
    List<EventEntity> findByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e FROM EventEntity e WHERE e.venue.id = :venueId")
    List<EventEntity> findByVenueId(@Param("venueId") Long venueId);

    @Query("SELECT e FROM EventEntity e WHERE e.capacity >= :minCapacity")
    List<EventEntity> findByMinCapacity(@Param("minCapacity") int minCapacity);
}
