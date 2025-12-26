# 1) Engineer Task Management Backend

A Spring Boot REST API for managing engineers and tracking tasks assigned to them.
Built to practice **production-style backend architecture**, not demo CRUD.

---

## 2) Tech Stack

1. Java 21
2. Spring Boot 3.x
3. Spring Data JPA
4. PostgreSQL
5. Hibernate
6. Maven

---

## 3) Domain Model

1. Engineer
2. Task
3. One Engineer can have many Tasks
4. A Task can belong to one Engineer or be unassigned

---

## 4) Features

1. Create, update, delete engineers
2. Create, update, delete tasks
3. Assign tasks to engineers
4. Fetch all tasks of an engineer
5. Fetch engineer of a task
6. Filter tasks by status
7. Pagination on list APIs
8. Request validation
9. Global exception handling
10. Initial data population using `data.sql`

---

## 5) API Endpoints

### 5.1) Engineer APIs

1. `GET /api/v1/engineers?page=&size=`
2. `GET /api/v1/engineers/{id}`
3. `POST /api/v1/engineers`
4. `PUT /api/v1/engineers/{id}`
5. `DELETE /api/v1/engineers/{id}`
6. `GET /api/v1/engineers/{id}/tasks`

### 5.2) Task APIs

1. `GET /api/v1/tasks?page=&size=`
2. `GET /api/v1/tasks/{id}`
3. `POST /api/v1/tasks`
4. `PUT /api/v1/tasks/{id}`
5. `DELETE /api/v1/tasks/{id}`
6. `PUT /api/v1/tasks/{taskId}/assign/{engineerId}`
7. `GET /api/v1/tasks/{id}/engineer`
8. `GET /api/v1/tasks/filter?status=OPEN`

---

## 6) Architecture

1. Controller layer handles HTTP requests and DTOs
2. Service layer contains business logic and works with entities
3. Repository layer handles database access
4. DTOs define API contracts
5. Mappers convert between entities and DTOs
6. Global exception handler provides consistent error responses

---

## 7) Project Structure

1. controller – REST controllers
2. service – business logic
3. repository – JPA repositories
4. dto – request and response objects
5. mapper – entity-DTO conversion
6. model – JPA entities
7. exception – global error handling

---

## 8) How to Run

1. Configure PostgreSQL and create a database
2. Update database credentials in `application.yml`
3. Run the Spring Boot application
4. Access APIs at `http://localhost:8080`

---

## 9) Design Notes

1. No authentication implemented intentionally
2. Focused on clean layering and backend fundamentals
3. Designed to be easily explainable in interviews
