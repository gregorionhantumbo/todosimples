package com.softdeving.todosimples.services;

import com.softdeving.todosimples.models.Task;
import com.softdeving.todosimples.models.User;
import com.softdeving.todosimples.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskService(UserService userService, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada! ID: " + id));
    }

    public List<Task> findAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Transactional
    public Task create(Task obj) {
        User user = userService.findById(obj.getUser().getId());
        obj.setId(null); // Garante que estamos criando uma nova tarefa
        obj.setUser(user);
        return taskRepository.save(obj);
    }

    @Transactional
    public Task update(Task obj) {
        Task existingTask = findById(obj.getId());
        existingTask.setDescription(obj.getDescription());
        return taskRepository.save(existingTask);
    }

    @Transactional
    public void delete(Long id) {
        findById(id); // Garante que a tarefa existe antes de excluir
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir essa tarefa!");
        }
    }
}
