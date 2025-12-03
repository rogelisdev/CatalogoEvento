🎟️ Online Ticket Catalog API - Core V2 (Persistence & Advanced Features)

Project: REST Service for managing an Event and Venue Catalog.

This is the second major version of the Event Catalog. This version migrates from in-memory storage to real persistence using Spring Data JPA with H2, and introduces critical enhancements such as robust validation, flexible pagination, and standardized error handling (Advice).

🎯 Project Objective

The main goal is to establish a production-grade API by:

Persistence: Complete migration of the data layer from memory to a relational database (H2 in-memory).

Robustness: Implementation of validations in input DTOs and centralized exception handling (400, 404, 409).

Performance: Full support for pagination and filtering in main queries.

🏗️ Architecture and Key Features

The project maintains the layered architecture (Controller, Service, Repository) but replaces the storage mechanism.

Component

New Feature / Description

Related Task

Persistence

Integration of Spring Data JPA and H2 Database. EventRepository and VenueRepository extend JpaRepository.

Task 1

Validations

Strict use of @Valid, @NotBlank, @Future, @Positive on DTOs (EventRequest, VenueRequest).

Task 2

Error Handling

Implementation of @ControllerAdvice (GlobalExceptionHandler) to centralize errors (400 Bad Request, 404 Not Found, 409 Conflict).

Task 4

Pagination

The /events endpoint supports the Pageable object (page, size, sort).

Task 3

Filters

The service implements filtering by city, category, and startDate.

Task 3

Package Structure

Package

Content

controller

Exposes the REST endpoints (EventController, VenueController).

service

Contains business logic and uses the persistence layer.

repository

Data access interfaces (JpaRepository).

domain

Entities mapped to the DB (EventEntity, VenueEntity).

dto

Input/Output DTOs (EventRequest, DetailsEventResponse).

advice

Error handling components (GlobalExceptionHandler, ErrorResponse).

💻 API Endpoints

The following endpoints are accessible, highlighting the pagination and filtering improvements in the main query. The API base path is /api/.

Events (/api/events)

HTTP Method

Endpoint

Description

Parameters (Task 3)

POST

/

Creates a new Event. (Subject to Validations and 409 Conflict)

-

GET

/

Lists Events with Pagination and Filters.

page, size, sort, city, category, startDate

GET

/{id}

Full Details of Event + Venue (DTO).

-

PUT

/{id}

Updates an existing Event.

-

DELETE

/{id}

Deletes an Event by ID. (Returns 404 if not found)

-

Venues (/api/venues)

Note: Assuming VenueController follows a standard CRUD with validations.

HTTP Method

Endpoint

Description

POST

/

Creates a new Venue.

GET

/

Lists all Venues.

GET

/{id}

Retrieves the details of a Venue.

PUT

/{id}

Updates an existing Venue.

DELETE

/{id}

Deletes a Venue by ID.

⚙️ Configuration and Execution

Technologies Used

Component

Description

Java 17+

Programming language.

Spring Boot 3.x

Application framework.

Spring Data JPA

ORM Persistence.

H2 Database

In-memory database.

Lombok

Boilerplate code reduction.


Compile and Run (with Maven):

./mvnw clean install
./mvnw spring-boot:run



Access the Documentation (Swagger UI):

Once the application is running (usually on port 8080), open your browser and visit:

http://localhost:8080/swagger-ui.html

