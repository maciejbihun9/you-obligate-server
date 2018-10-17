package com.maciejbihun.controllers;

import com.maciejbihun.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<User> getUser(Long id);
    ResponseEntity<User> saveUser(User user);
}
