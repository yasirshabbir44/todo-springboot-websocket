package com.example.demo.repository;

import com.example.demo.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testSaveTodo() {
        // Create a new Todo
        Todo todo = new Todo("Test Todo", "This is a test todo", false);
        
        // Save the Todo
        Todo savedTodo = todoRepository.save(todo);
        
        // Verify the Todo was saved with an ID
        assertNotNull(savedTodo.getId());
        assertEquals("Test Todo", savedTodo.getTitle());
    }

    @Test
    void testFindById() {
        // Create and persist a Todo
        Todo todo = new Todo("Test Todo", "This is a test todo", false);
        entityManager.persist(todo);
        entityManager.flush();
        
        // Find the Todo by ID
        Optional<Todo> foundTodo = todoRepository.findById(todo.getId());
        
        // Verify the Todo was found
        assertTrue(foundTodo.isPresent());
        assertEquals("Test Todo", foundTodo.get().getTitle());
    }

    @Test
    void testFindAll() {
        // Create and persist multiple Todos
        Todo todo1 = new Todo("Todo 1", "Description 1", false);
        Todo todo2 = new Todo("Todo 2", "Description 2", true);
        
        entityManager.persist(todo1);
        entityManager.persist(todo2);
        entityManager.flush();
        
        // Find all Todos
        List<Todo> todos = todoRepository.findAll();
        
        // Verify all Todos were found
        assertEquals(2, todos.size());
    }

    @Test
    void testUpdateTodo() {
        // Create and persist a Todo
        Todo todo = new Todo("Original Title", "Original Description", false);
        entityManager.persist(todo);
        entityManager.flush();
        
        // Update the Todo
        Todo todoToUpdate = todoRepository.findById(todo.getId()).orElseThrow();
        todoToUpdate.setTitle("Updated Title");
        todoToUpdate.setCompleted(true);
        todoRepository.save(todoToUpdate);
        
        // Verify the Todo was updated
        Todo updatedTodo = todoRepository.findById(todo.getId()).orElseThrow();
        assertEquals("Updated Title", updatedTodo.getTitle());
        assertTrue(updatedTodo.isCompleted());
    }

    @Test
    void testDeleteTodo() {
        // Create and persist a Todo
        Todo todo = new Todo("Todo to Delete", "This todo will be deleted", false);
        entityManager.persist(todo);
        entityManager.flush();
        
        // Delete the Todo
        todoRepository.deleteById(todo.getId());
        
        // Verify the Todo was deleted
        Optional<Todo> deletedTodo = todoRepository.findById(todo.getId());
        assertFalse(deletedTodo.isPresent());
    }

    @Test
    void testFindByCompleted() {
        // Create and persist Todos with different completion status
        Todo todo1 = new Todo("Todo 1", "Description 1", false);
        Todo todo2 = new Todo("Todo 2", "Description 2", true);
        Todo todo3 = new Todo("Todo 3", "Description 3", true);
        
        entityManager.persist(todo1);
        entityManager.persist(todo2);
        entityManager.persist(todo3);
        entityManager.flush();
        
        // Find completed Todos
        List<Todo> completedTodos = todoRepository.findByCompleted(true);
        
        // Verify only completed Todos were found
        assertEquals(2, completedTodos.size());
        assertTrue(completedTodos.stream().allMatch(Todo::isCompleted));
    }
}