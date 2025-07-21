package com.smartbooking.appointment.service;


import org.springframework.http.ResponseEntity;
import com.smartbooking.appointment.entity.User;
import com.smartbooking.appointment.dto.LoginDTO;

public interface UserService {
    User register(User user);
    String login(LoginDTO loginDTO);

}
