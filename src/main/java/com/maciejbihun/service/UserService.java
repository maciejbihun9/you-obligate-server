package com.maciejbihun.service;

import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Maciej Bihun
 */
public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    UserPrincipal loadUserByUsername(String username);
}
