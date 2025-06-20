# Todo Application

A full-stack web application for managing todos, built with Spring Boot and an in-memory H2 database.

## Features

- Create, read, update, and delete todos
- Mark todos as completed or incomplete
- Filter todos by completion status
- In-memory database with initial sample data
- RESTful API for todo operations
- Simple and responsive user interface

## Technologies Used

- **Backend**:
  - Spring Boot 3.5.3
  - Spring Data JPA
  - Spring MVC
  - H2 Database (in-memory)
  - JUnit 5 for testing
  - Mockito for mocking in tests

- **Frontend**:
  - HTML5
  - CSS3 (Bootstrap 5)
  - JavaScript (Vanilla JS)
  - Thymeleaf for server-side rendering

## Project Structure

The application follows a standard layered architecture:

- **Model**: Represents the data entities
- **Repository**: Handles data access operations
- **Service**: Contains business logic
- **Controller**: Handles HTTP requests and responses
- **View**: Thymeleaf templates for the user interface

## API Endpoints

| Method | URL                    | Description                   |
|--------|------------------------|-------------------------------|
| GET    | /api/todos             | Get all todos                 |
| GET    | /api/todos/{id}        | Get a specific todo by ID     |
| POST   | /api/todos             | Create a new todo             |
| PUT    | /api/todos/{id}        | Update an existing todo       |
| DELETE | /api/todos/{id}        | Delete a todo                 |
| GET    | /api/todos/completed   | Get all completed todos       |
| GET    | /api/todos/incomplete  | Get all incomplete todos      |

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Gradle:
   ```
   ./gradlew bootRun
   ```
4. Open a web browser and go to `http://localhost:8080`

## Testing

The application includes comprehensive tests for all layers:

- Entity tests
- Repository tests
- Service tests
- Controller tests

To run the tests:
```
./gradlew test
```

## Initial Data

When the application starts, it automatically loads the following sample todos:

1. "Buy groceries" (incomplete)
2. "Finish project" (incomplete)
3. "Call mom" (incomplete)
4. "Pay bills" (completed)
5. "Go to gym" (completed)

## Development Approach

This project was developed using Test-Driven Development (TDD):

1. Write tests first
2. Implement the code to make the tests pass
3. Refactor the code while keeping the tests passing

## License

This project is open source and available under the [MIT License](LICENSE).