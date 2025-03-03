package com.softdeving.todosimples.services;

import com.softdeving.todosimples.models.Task;
import com.softdeving.todosimples.models.User;
import com.softdeving.todosimples.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final TaskRepository taskRepository;

//    public TaskService(UserService userService, TaskRepository taskRepository) {
//        this.userService = userService;
//        this.taskRepository = taskRepository;
//    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada! ID: " + id));
    }

    public List<Task> findAllByUserId(Long userId) {
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
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
