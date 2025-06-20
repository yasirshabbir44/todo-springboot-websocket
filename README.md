# Todo Application

A full-stack web application for managing todos, built with Spring Boot and an in-memory H2 database.

## Features

- Create, read, update, and delete todos
- Track todo status (TODO, IN_PROGRESS, COMPLETED)
- Filter todos by status
- In-memory database with initial sample data
- RESTful API for todo operations
- Real-time updates via WebSockets
- Simple and responsive user interface

## Technologies Used

- **Backend**:
  - Java 17
  - Spring Boot 3.5.3
  - Spring Data JPA
  - Spring MVC
  - Spring WebSocket
  - H2 Database (in-memory)
  - JUnit 5 for testing
  - Mockito for mocking in tests

- **Frontend**:
  - HTML5
  - CSS3 (Bootstrap 5)
  - JavaScript (Vanilla JS)
  - Thymeleaf for server-side rendering
  - SockJS and STOMP for WebSocket communication

## Project Structure

The application follows a standard layered architecture:

- **Model**: Represents the data entities
- **Repository**: Handles data access operations
- **Service**: Contains business logic
- **Controller**: Handles HTTP requests and responses
- **View**: Thymeleaf templates for the user interface

### Project Information

- **Group ID**: ae.smartdubai.iid
- **Version**: 0.0.1-SNAPSHOT

## API Endpoints

| Method | URL                       | Description                   |
|--------|---------------------------|-------------------------------|
| GET    | /api/todos                | Get all todos                 |
| GET    | /api/todos/{id}           | Get a specific todo by ID     |
| POST   | /api/todos                | Create a new todo             |
| PUT    | /api/todos/{id}           | Update an existing todo       |
| DELETE | /api/todos/{id}           | Delete a todo                 |
| GET    | /api/todos/completed      | Get all completed todos       |
| GET    | /api/todos/incomplete     | Get all incomplete todos      |
| GET    | /api/todos/status/todo    | Get todos with TODO status    |
| GET    | /api/todos/status/progress| Get todos with IN_PROGRESS status |
| GET    | /api/todos/status/{status}| Get todos by specific status  |

## WebSocket Communication

The application uses WebSockets to provide real-time updates when todos are created, updated, or deleted.

### WebSocket Endpoints

| Endpoint | Description                                |
|----------|--------------------------------------------|
| /ws      | WebSocket connection endpoint with SockJS  |

### Subscription Topics

| Topic         | Description                                |
|---------------|--------------------------------------------|
| /topic/todos  | Receive real-time updates about todos      |

### Message Format

When a todo is created, updated, or deleted, a message is sent to all connected clients with the following format:

```json
{
  "todo": {
    "id": 1,
    "title": "Example Todo",
    "description": "This is an example",
    "status": "TODO",
    "completed": false
  },
  "action": "CREATED"
}
```

The `action` field can have one of the following values: `CREATED`, `UPDATED`, or `DELETED`.

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

1. "Buy groceries" (TODO)
2. "Finish project" (IN_PROGRESS)
3. "Call mom" (TODO)
4. "Pay bills" (COMPLETED)
5. "Go to gym" (COMPLETED)

## Development Approach

This project was developed using Test-Driven Development (TDD):

1. Write tests first
2. Implement the code to make the tests pass
3. Refactor the code while keeping the tests passing

## License

This project is open source and available under the [MIT License](LICENSE).
