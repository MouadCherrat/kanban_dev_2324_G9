package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.TaskRepository;
import com.example.kanbansystem.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
     public List<Task> getTaskByName(String name){return taskRepository.findTaskByName(name);}
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }
    public Task saveTask(Task task){
        return taskRepository.save(task);
    }
    public String deleteTaskById(Long id){
        taskRepository.deleteById(id);
        return "Task with id "+id+" deleted successfully!";
    }
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

}
