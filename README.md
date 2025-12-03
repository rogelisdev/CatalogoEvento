Events & Venues – Hexagonal Architecture Refactor
📌 Overview

This project started as a simple MVC application without persistence, then evolved to store data in memory, and is now being refactored into a Hexagonal (Ports & Adapters) Architecture.
The goal is to decouple business logic from frameworks like Spring and JPA, improve maintainability, and enable easy unit testing.

🎯 Objective

Refactor the existing Events & Venues application so that:

Business logic is framework-independent

Persistence and API layers are cleanly separated

The application keeps the same CRUD functionality as before

🏗️ New Project Structure
dominio/                      # Pure domain (Event, Venue, business rules)
  ports/
    in/                       # Input ports (use cases)
    out/                      # Output ports (repositories)

aplicacion/usecase/           # Use case implementations

infraestructura/
  adapters/
    in/web/                   # REST controllers
    out/jpa/                  # JPA persistence adapters
  config/                     # Spring configuration

🔌 Ports & Adapters

Input ports: define use cases (e.g., CreateEventUseCase, ListVenuesUseCase)

Output ports: define required dependencies (e.g., EventRepositoryPort)

Adapters:

REST adapter exposes API endpoints

JPA adapter implements persistence using repositories

MapStruct is used to map domain models and JPA entities.

✅ Acceptance Criteria

Same CRUD behavior for Events and Venues

Domain is fully independent of Spring/JPA

Proper use of ports and adapters

MapStruct handles model conversions

REST API works with no breaking changes

Documentation reflects the new architecture
