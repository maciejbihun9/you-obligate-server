package com.maciejbihun.controllers;

import com.maciejbihun.models.UserRegisteredService;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author BHN
 */
public interface UserRegisteredServiceController {

    UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService);

    ResponseEntity<UserRegisteredService> getUserRegisteredService(Long id);

    List<UserRegisteredService> getAllUserRegisteredServices();

    ResponseEntity<String> deleteUserRegisteredService(Long id);

}
