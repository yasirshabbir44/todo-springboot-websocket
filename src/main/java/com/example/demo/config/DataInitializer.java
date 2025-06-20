package com.example.demo.config;

import com.example.demo.model.Status;
import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final TodoRepository todoRepository;

    @Autowired
    public DataInitializer(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Create sample developer and designer todos with different statuses
            // TODO status
            Todo todo1 = new Todo("Implement user authentication", "Add JWT-based authentication to the REST API", Status.TODO);
            Todo todo2 = new Todo("Fix responsive layout issues", "Address UI breakpoints for mobile and tablet views", Status.TODO);
            Todo todo3 = new Todo("Refactor database queries", "Optimize SQL queries for better performance", Status.TODO);

            // IN_PROGRESS status
            Todo todo4 = new Todo("Create UI component library", "Design and implement reusable UI components with documentation", Status.IN_PROGRESS);
            Todo todo5 = new Todo("Deploy application to production", "Configure CI/CD pipeline and deploy to cloud server", Status.IN_PROGRESS);
            Todo todo6 = new Todo("Implement search functionality", "Add full-text search with filters and sorting options", Status.IN_PROGRESS);
            Todo todo7 = new Todo("Design landing page", "Create wireframes and mockups for the new landing page", Status.IN_PROGRESS);

            // COMPLETED status
            Todo todo8 = new Todo("Set up automated testing", "Configure Jest and Cypress for frontend testing", Status.COMPLETED);
            Todo todo9 = new Todo("Optimize image loading", "Implement lazy loading and WebP format conversion", Status.COMPLETED);

            // More TODO status
            Todo todo10 = new Todo("Create API documentation", "Document all endpoints using Swagger/OpenAPI", Status.TODO);
            Todo todo11 = new Todo("Implement dark mode", "Add theme switching functionality and design dark theme", Status.TODO);
            Todo todo12 = new Todo("Fix memory leaks", "Identify and resolve memory leaks in the application", Status.TODO);

            // More IN_PROGRESS status
            Todo todo13 = new Todo("Add accessibility features", "Ensure WCAG 2.1 AA compliance across all pages", Status.IN_PROGRESS);
            Todo todo14 = new Todo("Implement caching strategy", "Set up Redis for API response caching", Status.IN_PROGRESS);

            // More COMPLETED status
            Todo todo15 = new Todo("Design system architecture", "Create diagrams for the new microservices architecture", Status.COMPLETED);

            // More mixed statuses
            Todo todo16 = new Todo("Implement real-time notifications", "Add WebSocket support for instant updates", Status.TODO);
            Todo todo17 = new Todo("Create animation library", "Design and implement reusable animations for UI elements", Status.IN_PROGRESS);
            Todo todo18 = new Todo("Optimize database indexes", "Review and improve database index performance", Status.COMPLETED);
            Todo todo19 = new Todo("Implement multi-language support", "Add i18n framework and translation workflow", Status.TODO);
            Todo todo20 = new Todo("Set up monitoring tools", "Configure Prometheus and Grafana for system monitoring", Status.COMPLETED);
            Todo todo21 = new Todo("Implement file upload feature", "Add secure file upload with validation and storage", Status.IN_PROGRESS);
            Todo todo22 = new Todo("Design mobile app UI", "Create mockups for the companion mobile application", Status.TODO);
            Todo todo23 = new Todo("Implement OAuth integration", "Add support for Google, Facebook, and GitHub login", Status.COMPLETED);
            Todo todo24 = new Todo("Optimize frontend bundle size", "Reduce JavaScript bundle size using code splitting", Status.IN_PROGRESS);
            Todo todo25 = new Todo("Create user onboarding flow", "Design and implement interactive user onboarding", Status.COMPLETED);

            // Save todos to database
            todoRepository.save(todo1);
            todoRepository.save(todo2);
            todoRepository.save(todo3);
            todoRepository.save(todo4);
            todoRepository.save(todo5);
            todoRepository.save(todo6);
            todoRepository.save(todo7);
            todoRepository.save(todo8);
            todoRepository.save(todo9);
            todoRepository.save(todo10);
            todoRepository.save(todo11);
            todoRepository.save(todo12);
            todoRepository.save(todo13);
            todoRepository.save(todo14);
            todoRepository.save(todo15);
            todoRepository.save(todo16);
            todoRepository.save(todo17);
            todoRepository.save(todo18);
            todoRepository.save(todo19);
            todoRepository.save(todo20);
            todoRepository.save(todo21);
            todoRepository.save(todo22);
            todoRepository.save(todo23);
            todoRepository.save(todo24);
            todoRepository.save(todo25);

            // Log the number of todos
            System.out.println("Initialized database with " + todoRepository.count() + " todos");

            // Log the distribution of todos by status
            System.out.println("TODO: " + todoRepository.findByStatus(Status.TODO).size() + " todos");
            System.out.println("IN_PROGRESS: " + todoRepository.findByStatus(Status.IN_PROGRESS).size() + " todos");
            System.out.println("COMPLETED: " + todoRepository.findByStatus(Status.COMPLETED).size() + " todos");
        };
    }
}
