package com.github.midnightph.taskflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.midnightph.taskflow.entity.Task;
import com.github.midnightph.taskflow.entity.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId, TaskStatus status);
    Optional<Task> findByIdAndUserId(Long id, Long userId);
}