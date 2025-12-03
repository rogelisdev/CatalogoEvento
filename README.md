# Event Catalog - Hexagonal Architecture with Advanced JPA

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Java](https://img.shields.io/badge/Java-17-orange)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green)]()
[![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-blue)]()

## 📌 Overview

Event Catalog is a Spring Boot application built with **Hexagonal Architecture** (Ports & Adapters) that manages events, venues, users, and tasks. The project demonstrates advanced JPA concepts, query optimization, transaction management, and database migrations with Flyway.

## 🎯 Key Features

- ✅ **Hexagonal Architecture** - Clean separation of concerns with ports and adapters
- ✅ **SOLID Principles** - Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- ✅ **Advanced JPA Relationships** - OneToMany, ManyToOne, ManyToMany with proper configuration
- ✅ **Query Optimization** - JPQL queries, Specifications, EntityGraph, N+1 prevention
- ✅ **Transaction Management** - Proper use of @Transactional with read/write optimization
- ✅ **Database Migrations** - Versioned migrations with Flyway
- ✅ **Domain Independence** - Pure domain models without framework dependencies

## 🏗️ Architecture

### Hexagonal Architecture Layers

```
┌─────────────────────────────────────────────────────────┐
│                    Infrastructure                        │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ Controllers  │  │  JPA Repos   │  │   Entities   │  │
│  │  (Inbound)   │  │  (Outbound)  │  │   (Mappers)  │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                          ▲
                          │
┌─────────────────────────────────────────────────────────┐
│                     Application                          │
│  ┌──────────────────────────────────────────────────┐   │
│  │            Use Case Implementations              │   │
│  │  (CreateEvent, GetEvent, UpdateVenue, etc.)      │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                          ▲
                          │
┌─────────────────────────────────────────────────────────┐
│                       Domain (Core)                      │
│  ┌──────────────┐  ┌──────────────┐                     │
│  │    Models    │  │    Ports     │                     │
│  │ Event, Venue │  │  in / out    │                     │
│  │ User, Task   │  │ (Interfaces) │                     │
│  └──────────────┘  └──────────────┘                     │
└─────────────────────────────────────────────────────────┘
```

### Project Structure

```
src/main/java/com/codeup/catalogoDeEventos/
├── domain/
│   ├── models/              # Pure domain entities (Event, Venue, User, Task)
│   └── ports/
│       ├── in/              # Use case interfaces (inbound ports)
│       └── out/             # Repository interfaces (outbound ports)
├── application/
│   ├── dto/                 # Data Transfer Objects
│   └── usecase/             # Use case implementations
└── infrastructure/
    ├── adapters/            # JPA repository adapters
    ├── controller/          # REST controllers
    ├── entities/            # JPA entities with mappers
    ├── repositories/        # Spring Data JPA repositories
    └── specifications/      # JPA Specifications for dynamic queries
```

## 🗄️ Database Schema

### Entities and Relationships

- **Venue** (1) → (N) **Event** - OneToMany/ManyToOne
- **User** (1) → (N) **Task** - OneToMany/ManyToOne
- **User** (N) ↔ (N) **Event** - ManyToMany (user_event join table)

### Flyway Migrations

- `V1__initial_schema.sql` - Creates venues and events tables
- `V2__user_task_tables.sql` - Creates users, tasks, and user_event tables

All migrations include:
- Proper constraints (NOT NULL, UNIQUE, CHECK)
- Foreign keys with CASCADE
- Indexes on foreign keys and frequently queried columns

## 🚀 Query Optimization

### N+1 Query Prevention

1. **@EntityGraph** - Fetch associations in single query
   ```java
   @EntityGraph(attributePaths = {"venue"})
   @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
   Optional<EventEntity> findByIdWithVenue(@Param("id") Long id);
   ```

2. **@BatchSize** - Batch fetch collections
   ```java
   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
   @BatchSize(size = 10)
   private List<TaskEntity> tasks;
   ```

3. **Hibernate Batch Settings**
   - `hibernate.default_batch_fetch_size=10`
   - `hibernate.jdbc.batch_size=20`

### JPQL Queries

Custom JPQL queries for:
- Date range filtering
- Venue-based event lookup
- Capacity filtering
- User-task associations

### Specifications Pattern

Dynamic filtering with type-safe queries:
```java
Specification<TaskEntity> spec = TaskSpecifications
    .hasUserId(userId)
    .and(TaskSpecifications.hasStatus(status))
    .and(TaskSpecifications.titleContains(keyword));
```

## 💾 Transaction Management

### Write Operations
```java
@Transactional
public class CreateEventUseCaseImpl implements CreateEventUseCase {
    // Atomic write operations with default propagation
}
```

### Read Operations
```java
@Transactional(readOnly = true)
public class GetEventUseCaseImpl implements GetEventUseCase {
    // Optimized read-only transactions
}
```

## 🔧 Technologies

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Hibernate** (with statistics and query logging)
- **Flyway** (database migrations)
- **MySQL** (production database)
- **H2** (testing database)
- **Lombok** (boilerplate reduction)
- **OpenAPI/Swagger** (API documentation)
- **Maven** (build tool)

## 📦 Installation & Setup

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Configuration

Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build & Run

```bash
# Clean and compile
./mvnw clean compile

# Run application
./mvnw spring-boot:run

# Run tests
./mvnw test
```

### Flyway Migrations

Migrations run automatically on startup. To manually control:
```bash
# Migrate
./mvnw flyway:migrate

# Validate
./mvnw flyway:validate

# Info
./mvnw flyway:info
```

## 📊 Performance Monitoring

SQL logging is enabled for performance analysis:
```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
logging.level.org.hibernate.stat=DEBUG
spring.jpa.properties.hibernate.generate_statistics=true
```

## ✅ SOLID Principles Compliance

- **SRP**: Each class has one responsibility (use cases, repositories, entities)
- **OCP**: Extension via interfaces without modification
- **LSP**: Implementations properly substitute interfaces
- **ISP**: Focused interfaces (CreateEventUseCase, GetEventUseCase, etc.)
- **DIP**: Dependencies on abstractions (ports), not implementations

## 🎯 Acceptance Criteria Met

✅ OneToMany, ManyToOne, ManyToMany relationships correctly configured  
✅ Lazy/Eager loading properly configured with N+1 prevention  
✅ Optimized queries with JPQL and Specifications  
✅ Correct @Transactional usage for read/write operations  
✅ Versioned Flyway migrations (reproducible in any environment)  
✅ Domain independence maintained (no framework dependencies)  
✅ SOLID principles followed throughout  
✅ Hexagonal Architecture properly implemented  

## 📝 API Documentation

Once running, access Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## 🧪 Testing

The architecture enables easy testing:
- **Domain**: Pure unit tests without infrastructure
- **Use Cases**: Tests with mock repositories
- **Adapters**: Integration tests with test database

## 📄 License

This project is for educational purposes.

## 👥 Contributors

Developed as a demonstration of enterprise-grade Spring Boot application with Hexagonal Architecture and advanced JPA techniques.
