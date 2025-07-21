package com.smartbooking.appointment.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;


    public enum Role {
        admin,customer
    }

    @Column(name = "is_Active")
    private boolean isActive = true; // Java default, DB default is handled by schema

    @Column(name = "create_At", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;
}
