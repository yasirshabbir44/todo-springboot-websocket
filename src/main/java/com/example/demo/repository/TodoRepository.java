package com.example.demo.repository;

import com.example.demo.model.Status;
import com.example.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Find todos by completion status (for backward compatibility)
     * 
     * @param completed the completion status
     * @return a list of todos with the specified completion status
     */
    default List<Todo> findByCompleted(boolean completed) {
        return completed ? findByStatus(Status.COMPLETED) : 
                          findAll().stream()
                                  .filter(todo -> todo.getStatus() != Status.COMPLETED)
                                  .toList();
    }

    /**
     * Find todos by status
     * 
     * @param status the status
     * @return a list of todos with the specified status
     */
    List<Todo> findByStatus(Status status);
}
