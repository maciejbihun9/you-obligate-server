package com.maciejbihun.controller;

import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author BHN
 */
public interface UserController {
    ResponseEntity<List<UserDto>> getAllUsers();
    ResponseEntity<UserDto> createUser(UserDto userDto);
    ResponseEntity<UserDto> saveUserData(UserDto userDto);
}
