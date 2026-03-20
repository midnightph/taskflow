package com.github.midnightph.taskflow.dto;

import com.github.midnightph.taskflow.entity.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        @NotBlank(message = "Título é obrigatório")
        String title,

        String description,

        @NotNull(message = "Status é obrigatório")
        TaskStatus status
) {}