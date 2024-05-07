package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.TaskRepository;
import com.example.kanbansystem.entities.Task;
import com.example.kanbansystem.entities.TaskStatus;
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
    public List<Task> getTasksByBoardId(Long boardId) {
        return taskRepository.findByBoardId(boardId);
    }

    public void updateTaskStatus(Long taskId, TaskStatus status) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(status);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with id " + taskId);
        }
    }
}
