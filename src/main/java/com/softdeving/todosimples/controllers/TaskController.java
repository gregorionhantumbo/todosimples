package com.softdeving.todosimples.controllers;

import com.softdeving.todosimples.models.Task;
import com.softdeving.todosimples.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAll(@PathVariable Long userId) {
        List<Task> tasks = taskService.findAllByUserId(userId);
        System.out.println("Tarefas para o usuário " + userId + ": " + tasks); // Verifique as tarefas no log
        return ResponseEntity.ok().body(tasks);
    }



    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody Task obj) {
        Task createdTask = taskService.create(obj);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@Valid @RequestBody Task obj, @PathVariable Long id) {
        obj.setId(id);
        Task updatedTask = taskService.update(obj);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
