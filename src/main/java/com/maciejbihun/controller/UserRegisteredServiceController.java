package com.maciejbihun.controller;

import com.maciejbihun.models.UserRegisteredService;
import org.springframework.http.ResponseEntity;

/**
 * @author BHN
 */
public interface UserRegisteredServiceController {

    UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService);

    ResponseEntity<UserRegisteredService> getUserRegisteredService(Long id);

    ResponseEntity<String> deleteUserRegisteredService(Long id);

}
