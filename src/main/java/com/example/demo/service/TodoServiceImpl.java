package com.example.demo.service;

import com.example.demo.model.Status;
import com.example.demo.model.Todo;
import com.example.demo.model.TodoAction;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final WebSocketService webSocketService;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, WebSocketService webSocketService) {
        this.todoRepository = todoRepository;
        this.webSocketService = webSocketService;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    @Override
    public Todo createTodo(Todo todo) {
        // Ensure new todos have a status set
        if (todo.getStatus() == null) {
            todo.setStatus(Status.TODO);
        }
        Todo createdTodo = todoRepository.save(todo);

        // Send WebSocket notification about the new todo
        webSocketService.sendTodoUpdate(createdTodo, TodoAction.CREATED);

        return createdTodo;
    }

    @Override
    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = getTodoById(id);

        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());

        // Update status if provided
        if (todoDetails.getStatus() != null) {
            todo.setStatus(todoDetails.getStatus());
        } else {
            // For backward compatibility
            todo.setCompleted(todoDetails.isCompleted());
        }

        Todo updatedTodo = todoRepository.save(todo);

        // Send WebSocket notification about the updated todo
        webSocketService.sendTodoUpdate(updatedTodo, TodoAction.UPDATED);

        return updatedTodo;
    }

    @Override
    public void deleteTodo(Long id) {
        // Get the todo before deleting it so we can send its info in the WebSocket message
        Todo todoToDelete = getTodoById(id);

        // Delete todo
        todoRepository.deleteById(id);

        // Send WebSocket notification about the deleted todo
        webSocketService.sendTodoUpdate(todoToDelete, TodoAction.DELETED);
    }

    @Override
    public List<Todo> getCompletedTodos() {
        return todoRepository.findByStatus(Status.COMPLETED);
    }

    @Override
    public List<Todo> getIncompleteTodos() {
        // Return todos that are either in TODO or IN_PROGRESS status
        List<Todo> todoItems = new ArrayList<>(todoRepository.findByStatus(Status.TODO));
        todoItems.addAll(todoRepository.findByStatus(Status.IN_PROGRESS));
        return todoItems;
    }

    @Override
    public List<Todo> getTodosByStatus(Status status) {
        return todoRepository.findByStatus(status);
    }

    @Override
    public List<Todo> getInProgressTodos() {
        return todoRepository.findByStatus(Status.IN_PROGRESS);
    }
}
