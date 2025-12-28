# Store App

A simple Spring Boot backend application for managing a store system. It provides REST APIs for products, categories, customers, orders, and integrates with external services.

---

## Main Features

- **CRUD operations** for:
    - Products
    - Categories
    - Customers
    - Orders & Order Items
- **External API integration** for dummy products using WebClient (Reactive).
- **Redis caching** for frequently accessed data.
- **AOP-based time measurement** for service method execution.
- **Database migrations** using Flyway.

---

## Tech Stack

### Core
- Java 17
- Spring Boot 3.x
- Maven For Dependency Management

### Persistence
- Spring Data JPA
- MySQL
- Flyway (DB migrations)
- HikariCP (connection pooling)

### Caching
- Spring Cache Abstraction
- Redis Cache

### Web
- Spring Web (REST)
- Spring WebFlux (Reactive APIs)
- WebClient (external API calls)

### Mapping & Validation
- MapStruct (DTO ↔ Entity mapping)
- Jakarta Validation (Bean Validation)

### Cross-Cutting Concerns
- Spring AOP (method execution time logging)

---

## Architecture Overview

- **Controller layer**  
  Exposes REST endpoints and handles request validation.

- **Service layer**  
  Contains business logic, caching annotations, and transactional operations.

- **Repository layer**  
  Uses Spring Data JPA with optimized queries.

- **Mapper layer**  
  Converts between entities and DTOs using MapStruct.

- **Config layer**  
  Centralized configuration for Redis, WebClient, JPA auditing, and connection pooling.

---

## OpenAPI & Swagger

This project uses **Springdoc OpenAPI** to generate API documentation automatically.

- **Swagger UI**
  ```text
  http://localhost:8080/swagger
  ```

- **OpenAPI JSON**
  ```text
  http://localhost:8080/api-docs
  ```

---

## Configuration

- Environment variables are loaded from `.env`.
- Centralized application configuration via `application.yml`.
