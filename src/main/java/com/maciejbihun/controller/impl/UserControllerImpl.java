package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserController;
import com.maciejbihun.converters.UserConverter;
import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.User;
import com.maciejbihun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maciej Bihun
 */
@Controller
public class UserControllerImpl implements UserController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        userService.getAllUsers().forEach(user -> {
            userDtos.add(UserConverter.convertToDto(user));
        });
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = UserConverter.convertToEntity(userDto);
        userDto = UserConverter.convertToDto(userService.saveUser(user));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserDto> saveUserData(@RequestBody UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = UserConverter.convertToEntity(userDto);
        userDto = UserConverter.convertToDto(userService.saveUser(user));
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

}
