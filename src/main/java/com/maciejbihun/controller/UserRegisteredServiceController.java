package com.maciejbihun.controller;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author BHN
 */
public interface UserRegisteredServiceController {

    UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService);

    ResponseEntity<UserRegisteredService> getUserRegisteredService(Long id);

    ResponseEntity<String> deleteUserRegisteredService(Long id);

    ResponseEntity<List<UserRegisteredService>> findByUserRegisteredServiceCategory(UserRegisteredServiceCategory userRegisteredServiceCategory);

}
