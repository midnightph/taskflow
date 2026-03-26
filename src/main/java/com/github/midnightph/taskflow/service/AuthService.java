package com.github.midnightph.taskflow.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.midnightph.taskflow.config.JwtService;
import com.github.midnightph.taskflow.dto.AuthResponse;
import com.github.midnightph.taskflow.dto.RegisterRequest;
import com.github.midnightph.taskflow.entity.User;
import com.github.midnightph.taskflow.exception.EmailAlreadyExistsException;
import com.github.midnightph.taskflow.repository.UserRepository;
import com.github.midnightph.taskflow.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) throws EmailAlreadyExistsException {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new EmailAlreadyExistsException("Email already exists");
        });
        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .build();
        userRepository.save(user);
        return new AuthResponse(
                jwtService.generateToken(user.getId()),
                user.getName(),
                user.getEmail());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        return new AuthResponse(
                jwtService.generateToken(user.getId()),
                user.getName(),
                user.getEmail());
    }

}
