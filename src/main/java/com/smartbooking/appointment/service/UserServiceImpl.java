package com.smartbooking.appointment.service;

import com.smartbooking.appointment.entity.User;
import com.smartbooking.appointment.dto.LoginDTO;
import com.smartbooking.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User register(User user) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
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

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Return dummy token or placeholder for now
        return "Login successful for user: " + user.getUsername();
    }
}
