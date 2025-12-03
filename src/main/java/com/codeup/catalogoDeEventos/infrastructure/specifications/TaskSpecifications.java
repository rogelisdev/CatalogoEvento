package com.codeup.catalogoDeEventos.infrastructure.specifications;

import com.codeup.catalogoDeEventos.infrastructure.entities.TaskEntity;
import com.codeup.catalogoDeEventos.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<TaskEntity> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<TaskEntity> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    public static Specification<TaskEntity> hasUser(UserEntity user) {
        return (root, query, criteriaBuilder) -> {
            if (user == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }

    public static Specification<TaskEntity> titleContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<TaskEntity> descriptionContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    // Combine multiple specifications
    public static Specification<TaskEntity> filterTasks(Long userId, String status, String keyword) {
        return Specification.allOf(
                hasUserId(userId),
                hasStatus(status),
                titleContains(keyword).or(descriptionContains(keyword)));
    }
}
