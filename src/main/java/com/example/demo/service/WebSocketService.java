package com.example.demo.service;

import com.example.demo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for sending WebSocket messages to clients
 */
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Send a notification about a todo update to all connected clients
     *
     * @param todo the updated todo
     * @param action the action performed (CREATED, UPDATED, DELETED)
     */
    public void sendTodoUpdate(Todo todo, String action) {
        // Create a simple message object with the todo and action
        TodoUpdateMessage message = new TodoUpdateMessage(todo, action);
        
        // Send to all clients subscribed to /topic/todos
        messagingTemplate.convertAndSend("/topic/todos", message);
    }

    /**
     * Inner class representing a todo update message
     */
    public static class TodoUpdateMessage {
        private Todo todo;
        private String action;

        public TodoUpdateMessage(Todo todo, String action) {
            this.todo = todo;
            this.action = action;
        }

        public Todo getTodo() {
            return todo;
        }

        public void setTodo(Todo todo) {
            this.todo = todo;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}