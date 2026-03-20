package com.github.midnightph.taskflow.dto;

public record AuthResponse(
        String token,
        String name,
        String email
) {}