package com.example.kanbansystem.controller;

import com.example.kanbansystem.entities.Task;
import com.example.kanbansystem.entities.TaskStatus;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.service.EmailService;
import com.example.kanbansystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmailService emailService;
    @GetMapping("/tasks/{taskName}")
    public ResponseEntity<List<Task>> getTaskByName(@PathVariable("taskName") String taskName) {
        List<Task> task = taskService.getTaskByName(taskName);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasksId/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTask() {
        List<Task> tasks = taskService.getAllTask();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    @PostMapping("/tasks")
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        Task savedTask = taskService.saveTask(task);
        List<User> users = savedTask.getUsers();
        if (users != null && !users.isEmpty()) {
            String subject = "You have been added to a task";
            String body = "You have been added to the task: " + savedTask.getName();
            for (User user : users) {
                System.out.println(user.getEmail());
                if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                    emailService.sendEmail(user.getEmail(), subject, body);
                } else {
                    System.out.println("User email is null or empty for user: " + user.getId());
                }
            }
        }
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/tasksDeleted/{taskId}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<String> deleteTaskById(@PathVariable("taskId") Long taskId) {
        String result = taskService.deleteTaskById(taskId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasks/board/{boardId}")
    public ResponseEntity<List<Task>> getTasksByBoardId(@PathVariable("boardId") Long boardId) {
        List<Task> tasks = taskService.getTasksByBoardId(boardId);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        try {
            taskService.updateTaskStatus(taskId, status);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
