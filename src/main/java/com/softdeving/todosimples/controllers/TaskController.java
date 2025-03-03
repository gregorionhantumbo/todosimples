package com.softdeving.todosimples.controllers;

import com.softdeving.todosimples.models.Task;
import com.softdeving.todosimples.services.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin("*") // Permite requisições do frontend
@Validated
@AllArgsConstructor
public class TaskController {
    @Autowired
    private final TaskService taskService;

//    public TaskController(TaskService taskService) {
//        this.taskService = taskService;
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.findAllByUserId(userId);
        return tasks.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Task>> findById(@PathVariable Long id) {
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok((List<Task>) obj);
    }

    @Transactional
    @PostMapping
    @Validated
    public ResponseEntity<Task> create(@Valid @RequestBody Task obj) {
        Task createdTask = taskService.create(obj);
        return ResponseEntity.ok(createdTask);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Validated @RequestBody Task obj, @PathVariable Long id){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
