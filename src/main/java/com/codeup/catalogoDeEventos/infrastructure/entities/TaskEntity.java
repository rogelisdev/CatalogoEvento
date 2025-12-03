package com.codeup.catalogoDeEventos.infrastructure.entities;

import com.codeup.catalogoDeEventos.domain.models.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static TaskEntity fromDomainModel(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setStatus(task.getStatus());
        return entity;
    }

    public Task toDomainModel() {
        return new Task(id, title, description, status);
    }
}
