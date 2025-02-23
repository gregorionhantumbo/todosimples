package com.softdeving.todosimples.services;

import com.softdeving.todosimples.models.Task;
import com.softdeving.todosimples.models.User;
import com.softdeving.todosimples.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class TaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException("Tarefa nao achado! id:" + id + ", Tipo:" + Task.class.getName()));
    }

    @Transactional
    public Task create(Task obj){
         User user = this.userService.findById(obj.getUser().getId());
         obj.setId(null);
         obj.setUser(user);
         obj = taskRepository.save(obj);
         return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try{
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Nao eh possivel excuir essa tarefa!");
        }
    }
}
