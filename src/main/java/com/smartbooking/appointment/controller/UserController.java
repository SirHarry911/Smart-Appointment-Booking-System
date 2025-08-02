package com.smartbooking.appointment.controller;

import com.smartbooking.appointment.dto.LoginDTO;
import com.smartbooking.appointment.entity.User;
import com.smartbooking.appointment.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User saved = userService.register(user);
        return ResponseEntity.ok(new ApiResponse("User registered", saved.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Data
    @AllArgsConstructor
    static class JwtResponse {
        private String token;
    }

    @Data
    @AllArgsConstructor
    static class ApiResponse {
        private String message;
        private String email;
    }
}
