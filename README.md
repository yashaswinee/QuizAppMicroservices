# Quiz Service Application

## Overview

This is a microservices-based Quiz Service application designed to manage quizzes and questions efficiently. Originally built as a monolithic application, it has been refactored into separate **Quiz Service** and **Question Service** microservices, communicating via **Netflix Eureka** for service discovery and an **API Gateway** for routing requests. The project follows a layered architecture (Controller, Service, DAO, DTO) to ensure modularity and maintainability, with a focus on backend functionality and minimal frontend involvement.

The application leverages **Spring Boot** for rapid development and includes dependencies like **Spring Data JPA**, **Spring Web**, **PostgreSQL**, **OpenFeign**, **Eureka Discovery Client/Server**, and **Lombok** to reduce boilerplate code. The services interact with a shared PostgreSQL database
 and are designed for scalability, with the Question Service running on two instances for load balancing.

<!-- ## Features

- **Quiz Service**: Create, retrieve, and submit quizzes with customizable difficulty and question count.
- **Question Service**: Manage questions (CRUD operations), generate quizzes, and calculate scores.
- **Service Discovery**: Uses Netflix Eureka for dynamic service registration and discovery.
- **API Gateway**: Centralized routing for all service endpoints with cross-cutting concerns like security and monitoring.
- **Scalability**: Question Service runs on two instances (ports 8080 and 8081) for load balancing.
- **Database**: PostgreSQL with JPA Repository for automated and encapsulated database operations. -->

## Technologies Used

- **Spring Boot**: Framework for building microservices with embedded Apache Tomcat.
- **Spring Data JPA**: Simplifies database operations with Hibernate as the JPA provider.
- **Spring Web**: Enables RESTful API development.
- **PostgreSQL**: Relational database with JDBC and R2DBC drivers.
- **Netflix Eureka**: Service registry for client discovery and load balancing.
- **OpenFeign**: Declarative REST client for inter-service communication.
- **API Gateway**: Spring Cloud Gateway for routing and cross-cutting concerns.
- **Lombok**: Reduces boilerplate code with annotations.

## Features and Architecture

The application follows a microservices architecture with the following components:

- **Quiz Service**: Handles quiz creation, retrieval, and submission. Runs on port **8090**.
- **Question Service**: Manages question CRUD operations, quiz generation, and scoring. Runs on ports **8080** and **8081** (two instances for scalability).
- **Service Registry**: Netflix Eureka server running on port **8761** for service discovery.
- **API Gateway**: Routes requests to appropriate services, running on port **8765**.
- **Database**: Shared PostgreSQL database (`quizdb`) for both services.

The project is structured with the following layers:

- **Controller**: Handles HTTP requests and responses.
- **Service**: Contains business logic.
- **DAO**: Abstracts database operations using JPA Repository.
- **DTO/Models**: Data transfer objects (e.g., `CreateDto`, `QuestionWrapper`, `QuizResponse`) for request/response handling.

## Getting Started

### Setup Instructions

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/your-username/quiz-service.git
   cd quiz-service
   ```

2. **Configure PostgreSQL**:

   - Create a database named `quizdb`.

   - Update the `application.properties` or `application.yml` files in the Quiz and Question Services with your PostgreSQL credentials:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/quizdb
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```

3. **Access the Eureka Dashboard**:

   - Open `http://localhost:8761` to verify that all services (Quiz, Question, and API Gateway) are registered.

## API Endpoints

All endpoints are accessible via the API Gateway at `http://localhost:8765`. Prefix the service-specific endpoints with `/QUIZ-SERVICE/` or `/QUESTION-SERVICE/`.

#### Quiz Service Endpoints

- **POST** `/QUIZ-SERVICE/quiz/create`
  - **Description**: Creates a new quiz.
  - **Request Body**: `CreateDto` (difficulty, number of questions, title).
  - **Response**: String confirmation.
- **GET** `/QUIZ-SERVICE/quiz/get/{id}`
  - **Description**: Retrieves question IDs for a quiz by ID.
  - **Response**: List of `QuestionWrapper` objects (questions without answers).
- **POST** `/QUIZ-SERVICE/quiz/submit/{id}`
  - **Description**: Submits quiz responses and returns the score.
  - **Request Body**: List of `QuizResponse` objects.
  - **Response**: Integer score.

#### Question Service Endpoints

- **GET** `/QUESTION-SERVICE/allquestions`
  - **Description**: Retrieves all questions.
  - **Response**: List of `Question` objects.
- **GET** `/QUESTION-SERVICE/difficulty/{diff}`
  - **Description**: Retrieves questions by difficulty level.
  - **Path Variable**: `diff` (e.g., "easy", "medium", "hard").
  - **Response**: List of `Question` objects.
- **POST** `/QUESTION-SERVICE/add`
  - **Description**: Adds a new question.
  - **Request Body**: `Question` object.
  - **Response**: String confirmation.
- **DELETE** `/QUESTION-SERVICE/delete/{id}`
  - **Description**: Deletes a question by ID.
  - **Path Variable**: `id` (question ID).
  - **Response**: String confirmation.
- **PUT** `/QUESTION-SERVICE/update`
  - **Description**: Updates an existing question.
  - **Request Body**: `Question` object.
  - **Response**: String confirmation.
- **GET** `/QUESTION-SERVICE/generate`
  - **Description**: Generates a quiz with random question IDs.
  - **Query Parameters**: `difficulty` (string), `NumQ` (integer).
  - **Response**: List of question IDs.
- **GET** `/QUESTION-SERVICE/getQuestions/{ids}`
  - **Description**: Retrieves questions by their IDs.
  - **Path Variable**: `ids` (comma-separated list of integers).
  - **Response**: List of `QuestionWrapper` objects.
- **POST** `/QUESTION-SERVICE/getScore`
  - **Description**: Calculates the score for submitted responses.
  - **Request Body**: List of `QuizResponse` objects.
  - **Response**: Integer score.

## Serivce Registry 

```
┌─────────────────┐    Register   ┌──────────────────┐
│   Quiz Service  │ ────────────► │   Eureka Server  │
│   (Port: 8090)  │               │   (Port: 8761)   │
└─────────────────┘               └──────────────────┘
                                          │
┌─────────────────┐    Register           │
│Question Service │ ──────────────────────┤
│   (Port: 8080)  │                       │
└─────────────────┘                       │
                                          │
┌─────────────────┐    Register           │
│Question Service │ ──────────────────────┤
│   (Port: 8081)  │                       │
└─────────────────┘                       │
                                          │
┌─────────────────┐    Register           │
│  API Gateway    │ ──────────────────────┘
│   (Port: 8765)  │
└─────────────────┘
```
Each microservice starts up and registers itself with Eureka Server. Registration includes: service name, IP address, port

## Complete Flow (creating quiz with questions) 

```
┌─────────────────┐  1. POST /quiz   ┌─────────────────┐
│     Client      │ ───────────────► │  API Gateway    │
│                 │                  │   (Port: 8765)  │
└─────────────────┘                  └─────────────────┘
                                              │
                                              │ 2. Route to
                                              │    quiz-service
                                              ▼
                                    ┌─────────────────┐
                                    │   Quiz Service  │
                                    │   (Port: 8090)  │
                                    └─────────────────┘
                                              │
                                              │ 3. Get questions
                                              │    for quiz
                                              ▼
                                    ┌──────────────────┐
                                    │   Eureka Server  │
                                    │   (Port: 8761)   │
                                    └──────────────────┘
                                              │
                                              │ 4. Service
                                              │    discovery
                                              ▼
                    ┌─────────────────┐              ┌─────────────────┐
                    │Question Service │              │Question Service │
                    │   (Port: 8080)  │   OR         │   (Port: 8081)  │
                    └─────────────────┘              └─────────────────┘
                              │                                │
                              │ 5. Database query              │
                              ▼                                ▼
                    ┌──────────────────────────────────────────────────┐
                    │              PostgreSQL Database                 │
                    │                   (quizdb)                       │
                    └──────────────────────────────────────────────────┘
```