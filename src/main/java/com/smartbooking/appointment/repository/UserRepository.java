package com.smartbooking.appointment.repository;

import com.smartbooking.appointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {

}