package com.example.demo.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void testTodoCreation() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("This is a test todo");
        todo.setCompleted(false);

        assertEquals(1L, todo.getId());
        assertEquals("Test Todo", todo.getTitle());
        assertEquals("This is a test todo", todo.getDescription());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testTodoConstructor() {
        Todo todo = new Todo("Test Todo", "This is a test todo", false);

        assertNull(todo.getId()); // ID should be null until saved
        assertEquals("Test Todo", todo.getTitle());
        assertEquals("This is a test todo", todo.getDescription());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testTodoEquality() {
        LocalDateTime now = LocalDateTime.now();

        Todo todo1 = new Todo("Test Todo", "This is a test todo", false);
        todo1.setId(1L);
        todo1.setCreatedAt(now);
        todo1.setUpdatedAt(now);

        Todo todo2 = new Todo("Test Todo", "This is a test todo", false);
        todo2.setId(1L);
        todo2.setCreatedAt(now);
        todo2.setUpdatedAt(now);

        Todo todo3 = new Todo("Different Todo", "This is a different todo", true);
        todo3.setId(2L);
        todo3.setCreatedAt(now.plusDays(1));
        todo3.setUpdatedAt(now.plusDays(1));

        assertEquals(todo1, todo2);
        assertNotEquals(todo1, todo3);
    }

    @Test
    void testDateTimeFields() {
        Todo todo = new Todo("Test Todo", "This is a test todo", false);

        // Manually trigger the JPA lifecycle methods
        todo.onCreate();

        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());

        // Simulate an update after a small delay
        LocalDateTime createdAt = todo.getCreatedAt();
        LocalDateTime updatedAt = todo.getUpdatedAt();

        try {
            // Add a small delay to ensure the timestamps are different
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }

        todo.onUpdate();

        assertEquals(createdAt, todo.getCreatedAt()); // createdAt should not change
        assertNotEquals(updatedAt, todo.getUpdatedAt()); // updatedAt should change
    }
}
