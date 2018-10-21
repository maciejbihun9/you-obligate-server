package com.maciejbihun.controller;

import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author BHN
 */
public interface UserService {
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> createUserAccount(User user);
    UserPrincipal loadUserByUsername(String username);
}
