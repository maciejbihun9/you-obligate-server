package com.maciejbihun.controllers;

import com.maciejbihun.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<User> getUser(Long id);
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> saveUser(User user);
}
