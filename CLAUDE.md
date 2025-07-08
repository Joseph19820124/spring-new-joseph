# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 4 demonstration project showcasing modern Java features and reactive programming. The project is written in Chinese and demonstrates:

- **Virtual Threads**: Java 21 Project Loom virtual threads for improved concurrency
- **Reactive Programming**: Spring WebFlux with Mono/Flux patterns
- **REST Client**: Spring Boot 3.x declarative HTTP client
- **Async Programming**: CompletableFuture integration
- **Containerization**: Complete Docker support

## Common Commands

```bash
# Local development
mvn spring-boot:run                                    # Run application on port 8080
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8088  # Run on different port
mvn test                                               # Run tests
mvn clean package                                      # Build JAR

# Docker
docker-compose up --build                              # Build and run with Docker
docker-compose up -d                                   # Run in background
docker-compose down                                    # Stop containers

# Testing
mvn test                                               # Run all tests
mvn test -Dtest=UserControllerTest                     # Run specific test class
```

## Architecture

### Core Structure
- **Package**: `com.example.demo`
- **Entry Point**: `SpringBoot4DemoApplication.java`
- **Architecture**: Layered (Controller → Service → Repository → Model)

### Key Components

**Controllers**:
- `UserController`: Traditional and reactive user management endpoints
- `ReactiveController`: Pure reactive programming examples with WebFlux

**Configuration**:
- `VirtualThreadConfig`: Configures virtual threads for Tomcat and async tasks
- `DataInitializer`: Seeds H2 database with initial data

**Virtual Thread Integration**:
- All HTTP requests use virtual threads via `TomcatProtocolHandlerCustomizer`
- Async operations use `Executors.newVirtualThreadPerTaskExecutor()`
- Thread names visible in logs as `VirtualThread[#xx]`

### Technology Stack
- **Java 21** with virtual threads
- **Spring Boot 3.3.5** (not actually Spring Boot 4 - this is a demo name)
- **Spring WebFlux** for reactive programming
- **H2 Database** in-memory with JPA
- **Lombok** for boilerplate reduction
- **Maven** for build management

## API Structure

### User Management (`/api/users`)
- Standard CRUD operations
- Async variants using `CompletableFuture`
- Reactive variants using `Mono<User>`
- SSE streaming endpoint

### Reactive Examples (`/api/reactive`)
- Mono/Flux demonstrations
- GitHub API integration
- Combined reactive streams
- Server-Sent Events

### Monitoring (`/actuator`)
- Health checks, metrics, environment info
- Configured to show detailed health information

## Development Notes

### Database Access
- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`, Password: (empty)

### Virtual Thread Verification
- Log output shows virtual thread names
- All request handling uses virtual threads
- Async operations leverage virtual thread pool

### Docker Configuration
- Multi-stage build with Maven and OpenJDK 21
- ZGC garbage collector enabled
- Non-root user for security
- Health checks configured

## Testing

- Uses standard Spring Boot Test framework
- Reactor Test for reactive components
- H2 in-memory database for tests
- Example test: `UserControllerTest`

## Configuration Files

- `application.yml`: Main configuration with virtual threads enabled
- `pom.xml`: Maven dependencies and build configuration
- `docker-compose.yml`: Container orchestration
- `Dockerfile`: Multi-stage container build

## Important Notes

- The project name suggests "Spring Boot 4" but actually uses Spring Boot 3.3.5
- All documentation and comments are in Chinese
- Virtual threads are the main performance feature being demonstrated
- The application showcases both traditional and reactive programming patterns