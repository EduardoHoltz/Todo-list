package com.carloseduardo.todo_list.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carloseduardo.todo_list.models.Task;
import com.carloseduardo.todo_list.models.User;
import com.carloseduardo.todo_list.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {

        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException(
                "Task not found, id: " + id + " | type: " + Task.class.getName()));
    }

    @Transactional
    public Task create(Task task) {

        User user = this.userService.findById(task.getUser().getId());

        task.setId(null);
        task.setUser(user);

        task = this.taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task update(Task task) {
        Task newTask = findById(task.getId());

        newTask.setDescription(task.getDescription());

        return this.taskRepository.save(newTask);
    }

    public void delete(Long id) {
        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } 
        
        catch (Exception exception) {
            throw new RuntimeException(
                "Something going failed while task deleting"
            );
        }
    }

}
