package com.example.demo.service;

import com.example.demo.model.Status;
import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private WebSocketService webSocketService;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo1;
    private Todo todo2;

    @BeforeEach
    void setUp() {
        todo1 = new Todo("Todo 1", "Description 1", Status.TODO);
        todo1.setId(1L);

        todo2 = new Todo("Todo 2", "Description 2", Status.COMPLETED);
        todo2.setId(2L);
    }

    @Test
    void testGetAllTodos() {
        // Arrange
        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        // Act
        List<Todo> todos = todoService.getAllTodos();

        // Assert
        assertEquals(2, todos.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testGetTodoById() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));

        // Act
        Todo foundTodo = todoService.getTodoById(1L);

        // Assert
        assertNotNull(foundTodo);
        assertEquals("Todo 1", foundTodo.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTodoByIdNotFound() {
        // Arrange
        when(todoRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> todoService.getTodoById(3L));
        verify(todoRepository, times(1)).findById(3L);
    }

    @Test
    void testCreateTodo() {
        // Arrange
        Todo newTodo = new Todo("New Todo", "New Description", Status.TODO);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

        // Act
        Todo createdTodo = todoService.createTodo(newTodo);

        // Assert
        assertNotNull(createdTodo);
        assertEquals("Todo 1", createdTodo.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testUpdateTodo() {
        // Arrange
        Todo updatedTodo = new Todo("Updated Todo", "Updated Description", Status.COMPLETED);
        updatedTodo.setId(1L);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        // Act
        Todo result = todoService.updateTodo(1L, updatedTodo);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Todo", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.isCompleted());
        assertEquals(Status.COMPLETED, result.getStatus());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testUpdateTodoNotFound() {
        // Arrange
        Todo updatedTodo = new Todo("Updated Todo", "Updated Description", Status.COMPLETED);
        updatedTodo.setId(3L);

        when(todoRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> todoService.updateTodo(3L, updatedTodo));
        verify(todoRepository, times(1)).findById(3L);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void testDeleteTodo() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        doNothing().when(todoRepository).deleteById(1L);

        // Act
        todoService.deleteTodo(1L);

        // Assert
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTodoNotFound() {
        // Arrange
        when(todoRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> todoService.deleteTodo(3L));
        verify(todoRepository, times(1)).findById(3L);
        verify(todoRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetCompletedTodos() {
        // Arrange
        when(todoRepository.findByStatus(Status.COMPLETED)).thenReturn(List.of(todo2));

        // Act
        List<Todo> completedTodos = todoService.getCompletedTodos();

        // Assert
        assertEquals(1, completedTodos.size());
        assertTrue(completedTodos.get(0).isCompleted());
        assertEquals(Status.COMPLETED, completedTodos.get(0).getStatus());
        verify(todoRepository, times(1)).findByStatus(Status.COMPLETED);
    }

    @Test
    void testGetIncompleteTodos() {
        // Arrange
        when(todoRepository.findByStatus(Status.TODO)).thenReturn(List.of(todo1));
        when(todoRepository.findByStatus(Status.IN_PROGRESS)).thenReturn(List.of());

        // Act
        List<Todo> incompleteTodos = todoService.getIncompleteTodos();

        // Assert
        assertEquals(1, incompleteTodos.size());
        assertFalse(incompleteTodos.get(0).isCompleted());
        assertEquals(Status.TODO, incompleteTodos.get(0).getStatus());
        verify(todoRepository, times(1)).findByStatus(Status.TODO);
        verify(todoRepository, times(1)).findByStatus(Status.IN_PROGRESS);
    }
}
