package com.github.midnightph.taskflow.controller;

import java.util.List;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.midnightph.taskflow.dto.TaskRequest;
import com.github.midnightph.taskflow.dto.TaskResponse;
import com.github.midnightph.taskflow.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    
    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getTasks() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(taskService.getAllTasks(userId));
        
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(taskService.getTaskById(userId, taskId));
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(taskService.createTask(userId, taskRequest));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskRequest taskRequest, @PathVariable Long taskId) throws TaskRejectedException {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(taskService.updateTask(userId, taskId, taskRequest));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long taskId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

}
