package com.smartbooking.appointment.dto;

import lombok.*;


@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // No-arg constructor
@AllArgsConstructor // All-args constructor

public class LoginDTO {
    private String email;
    private  String password;
}
