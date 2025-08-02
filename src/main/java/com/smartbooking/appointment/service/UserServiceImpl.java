package com.smartbooking.appointment.service;

import com.smartbooking.appointment.entity.User;
import com.smartbooking.appointment.dto.LoginDTO;
import com.smartbooking.appointment.repository.UserRepository;
import com.smartbooking.appointment.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User register(User user) {
        // Check if email already exists
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already registered");
        });

        // Hash password
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));

        // Set timestamps
        user.setCreatedAt(LocalDateTime.now());

        // Default role if not provided
        if (user.getRole() == null) {
            user.setRole(User.Role.customer);
        }

        // Save user
        return userRepository.save(user);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isPasswordValid = encoder.matches(loginDTO.getPassword(), user.getPasswordHash());
        if (!isPasswordValid) {
            throw new RuntimeException("Invalid credentials");
        }

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT (using email as subject, include role)
        String roleClaim = user.getRole() != null ? user.getRole().name() : User.Role.customer.name();
        return jwtUtil.generateToken(user.getEmail(), roleClaim);
    }
}
