package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Todo todo1;
    private Todo todo2;
    private List<Todo> todos;

    @BeforeEach
    void setUp() {
        todo1 = new Todo("Todo 1", "Description 1", false);
        todo1.setId(1L);

        todo2 = new Todo("Todo 2", "Description 2", true);
        todo2.setId(2L);

        todos = Arrays.asList(todo1, todo2);
    }

    @Test
    void testGetAllTodos() throws Exception {
        when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Todo 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Todo 2")));

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    void testGetTodoById() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(todo1);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Todo 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.completed", is(false)));

        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    void testGetTodoByIdNotFound() throws Exception {
        when(todoService.getTodoById(3L)).thenThrow(new RuntimeException("Todo not found with id: 3"));

        mockMvc.perform(get("/api/todos/3"))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).getTodoById(3L);
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo newTodo = new Todo("New Todo", "New Description", false);
        when(todoService.createTodo(any(Todo.class))).thenReturn(todo1);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Todo 1")));

        verify(todoService, times(1)).createTodo(any(Todo.class));
    }

    @Test
    void testUpdateTodo() throws Exception {
        Todo updatedTodo = new Todo("Updated Todo", "Updated Description", true);
        when(todoService.updateTodo(eq(1L), any(Todo.class))).thenReturn(updatedTodo);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Todo")))
                .andExpect(jsonPath("$.description", is("Updated Description")))
                .andExpect(jsonPath("$.completed", is(true)));

        verify(todoService, times(1)).updateTodo(eq(1L), any(Todo.class));
    }

    @Test
    void testUpdateTodoNotFound() throws Exception {
        Todo updatedTodo = new Todo("Updated Todo", "Updated Description", true);
        when(todoService.updateTodo(eq(3L), any(Todo.class)))
                .thenThrow(new RuntimeException("Todo not found with id: 3"));

        mockMvc.perform(put("/api/todos/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo)))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).updateTodo(eq(3L), any(Todo.class));
    }

    @Test
    void testDeleteTodo() throws Exception {
        doNothing().when(todoService).deleteTodo(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).deleteTodo(1L);
    }

    @Test
    void testDeleteTodoNotFound() throws Exception {
        doThrow(new RuntimeException("Todo not found with id: 3")).when(todoService).deleteTodo(3L);

        mockMvc.perform(delete("/api/todos/3"))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).deleteTodo(3L);
    }

    @Test
    void testGetCompletedTodos() throws Exception {
        when(todoService.getCompletedTodos()).thenReturn(List.of(todo2));

        mockMvc.perform(get("/api/todos/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].completed", is(true)));

        verify(todoService, times(1)).getCompletedTodos();
    }

    @Test
    void testGetIncompleteTodos() throws Exception {
        when(todoService.getIncompleteTodos()).thenReturn(List.of(todo1));

        mockMvc.perform(get("/api/todos/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].completed", is(false)));

        verify(todoService, times(1)).getIncompleteTodos();
    }
}