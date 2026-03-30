package com.github.midnightph.taskflow.controller;

import java.util.List;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.midnightph.taskflow.dto.TaskRequest;
import com.github.midnightph.taskflow.dto.TaskResponse;
import com.github.midnightph.taskflow.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(@RequestBody Long userId) {
        return ResponseEntity.ok(taskService.getAllTasks(userId));
        
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTask(@RequestBody Long taskId, @RequestBody Long userId) {
        return ResponseEntity.ok(taskService.getTaskById(userId, taskId));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest, @RequestBody Long userId) {
        return ResponseEntity.ok(taskService.createTask(userId, taskRequest));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskRequest taskRequest, @RequestBody Long userId, @RequestBody Long taskId) throws TaskRejectedException {
        return ResponseEntity.ok(taskService.updateTask(userId, taskId, taskRequest));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object> deleteTask(@RequestBody Long taskId, @RequestBody Long userId) {
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

}
