package com.example.demo.service;

import com.example.demo.model.Status;
import com.example.demo.model.Todo;
import java.util.List;

/**
 * Service interface for managing Todo entities
 */
public interface TodoService {

    /**
     * Get all todos
     * 
     * @return list of all todos
     */
    List<Todo> getAllTodos();

    /**
     * Get a todo by its ID
     * 
     * @param id the todo ID
     * @return the todo with the specified ID
     * @throws RuntimeException if todo not found
     */
    Todo getTodoById(Long id);

    /**
     * Create a new todo
     * 
     * @param todo the todo to create
     * @return the created todo
     */
    Todo createTodo(Todo todo);

    /**
     * Update an existing todo
     * 
     * @param id the ID of the todo to update
     * @param todoDetails the updated todo details
     * @return the updated todo
     * @throws RuntimeException if todo not found
     */
    Todo updateTodo(Long id, Todo todoDetails);

    /**
     * Delete a todo
     * 
     * @param id the ID of the todo to delete
     * @throws RuntimeException if todo not found
     */
    void deleteTodo(Long id);

    /**
     * Get all completed todos
     * 
     * @return list of completed todos
     */
    List<Todo> getCompletedTodos();

    /**
     * Get all incomplete todos
     * 
     * @return list of incomplete todos
     */
    List<Todo> getIncompleteTodos();

    /**
     * Get todos by status
     * 
     * @param status the status to filter by
     * @return list of todos with the specified status
     */
    List<Todo> getTodosByStatus(Status status);

    /**
     * Get all in-progress todos
     * 
     * @return list of in-progress todos
     */
    List<Todo> getInProgressTodos();
}
