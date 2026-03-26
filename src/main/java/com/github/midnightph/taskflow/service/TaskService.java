package com.github.midnightph.taskflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.midnightph.taskflow.dto.TaskResponse;
import com.github.midnightph.taskflow.entity.Task;
import com.github.midnightph.taskflow.exception.ResourceNotFoundException;
import com.github.midnightph.taskflow.repository.TaskRepository;
import com.github.midnightph.taskflow.repository.UserRepository;
import com.github.midnightph.taskflow.dto.TaskRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskResponse> getAllTasks(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream().map(TaskResponse::from).toList();
    }

    public TaskResponse getTaskById(Long userId, Long taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return TaskResponse.from(task);
    }

    public TaskResponse createTask(Long userId, TaskRequest task) {
        Task newTask = task.toEntity();
        newTask.setUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return TaskResponse.from(taskRepository.save(newTask));
    }

    public TaskResponse updateTask(Long userId, Long taskId, TaskRequest task) throws ResourceNotFoundException {
        
        Task newTask = task.toEntity();
        Task taskToUpdate = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskToUpdate.setTitle(newTask.getTitle());
        taskToUpdate.setDescription(newTask.getDescription());
        taskToUpdate.setStatus(newTask.getStatus());
        
        return TaskResponse.from(taskRepository.save(taskToUpdate));
    }

    public void deleteTask(Long userId, Long taskId) throws ResourceNotFoundException {
        taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.deleteById(taskId);
    }

}
