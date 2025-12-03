# 🎟️ Online Ticket Catalog API - Core V1 (In-Memory)

This repository contains the initial version of an online ticketing catalog API. This core version is built as a **RESTful service** using **Spring Boot**, implementing a layered architecture (Controller, Service, Repository simulation), and utilizing **in-memory storage** (Java's `List<>`) instead of a persistent database.

---

## 🎯 Project Objective

The main goal of this project is to build the first functional iteration of the online ticket catalog, enabling the management of **Events** and **Venues** through a REST API. This task focuses on establishing the architectural foundations, implementing the complete CRUD operations, and setting up API documentation using OpenAPI/Swagger.

---

## 🏗️ Architecture and Features

The application follows a standard **layered architecture** and was developed with the following structure:

* **Controller Layer:** Handles HTTP requests and response mapping (`ResponseEntity`).
* **Service Layer:** Contains the business logic and manages the data storage.
* **Domain / DTO Layer:** Defines the core entities (`Event`, `Venue`) and the data transfer objects (`EventRequest`, `VenueRequest`, `DetailsEventResponse`).
* **Data Storage:** Simulated using an in-memory `List<T>` within the service implementation.

### Key Tasks Completed

| Task | Description |
| :--- | :--- |
| **Task 1: Architecture Setup** | Established the layered structure (DTOs, Services, Controllers). Configured in-memory services with `List<>` and `AtomicLong` for ID generation. Implemented standard HTTP codes and `ResponseEntity`. |
| **Task 2: CRUD Implementation** | Implemented the complete RESTful CRUD operations for both **Events** and **Venues**. Included minimal validation (e.g., non-empty names). |
| **Task 3: Documentation & Errors** | Configured **OpenAPI/Swagger UI** for complete and navigable API documentation. Ensured all endpoints have descriptions and examples. Implemented basic error handling (e.g., **404 Not Found** and **400 Bad Request**). |

---

## 💻 API Endpoints

The following RESTful endpoints are available for managing resources. The API base path is typically `/api/` (e.g., `/api/events`).

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/events` | Creates a new Event. |
| `GET` | `/events` | Retrieves a list of all Events. |
| `GET` | `/events/{id}` | Retrieves details for a specific Event. |
| `PUT` | `/events/{id}` | Updates all fields for a specific Event. |
| `DELETE` | `/events/{id}` | Deletes a specific Event. |
| `GET` | `/events/{id}/details` | Retrieves an Event with its associated Venue details (Simulated JOIN). |
| `POST` | `/venues` | Creates a new Venue. |
| `GET` | `/venues` | Retrieves a list of all Venues. |
| `GET` | `/venues/{id}` | Retrieves details for a specific Venue. |
| `PUT` | `/venues/{id}` | Updates all fields for a specific Venue. |
| `DELETE` | `/venues/{id}` | Deletes a specific Venue. |

---

## 🚀 Getting Started

### Prerequisites

* Java 17+ (or equivalent version used for the project)
* Maven or Gradle (used for dependency management)

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone [Your-Repo-URL]
    cd catalogoDeEventos
    ```

2.  **Build the project (using Maven as example):**
    ```bash
    ./mvnw clean install
    ```

3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```

### Accessing the Documentation

Once the application is running (default port is 8080), you can access the OpenAPI documentation (Swagger UI) at the following URL:

[http://localhost:8080/swagger-ui.html]

(http://localhost:8080/swagger-ui.html)

---

## ✅ Acceptance Criteria Summary

The project meets the following requirements for Core V1:

* Complete CRUD functionality is operational (without persistence).
* API adheres to REST principles and uses appropriate HTTP response codes (200, 201, 404, 400).
* The project structure is clearly organized by layers (Controller-Service).
* Data is stored and maintained in memory during the application's runtime.
* The Swagger UI is fully configured and provides accurate, navigable documentation for all endpoints.
````
