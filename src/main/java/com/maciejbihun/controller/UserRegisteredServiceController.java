package com.maciejbihun.controller;

import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.UserRegisteredService;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Maciej Bihun
 */
public interface UserRegisteredServiceController {

    ResponseEntity<UserRegisteredServiceDto> saveUserRegisteredService(UserRegisteredServiceDto userRegisteredServiceDto);

    ResponseEntity<UserRegisteredServiceDto> getUserRegisteredService(Long id);

    ResponseEntity<String> deleteUserRegisteredService(Long id);

    ResponseEntity<List<UserRegisteredServiceDto>> getUserRegisteredServices(String category);

}
