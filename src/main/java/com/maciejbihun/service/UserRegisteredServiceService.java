package com.maciejbihun.service;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;

import java.util.List;
import java.util.Optional;

public interface UserRegisteredServiceService {

    UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService);

    Optional<UserRegisteredService> getUserRegisteredService(Long id);

    List<UserRegisteredService> getUserRegisteredServices();

    List<UserRegisteredService> getUserRegisteredServicesByCategory(UserRegisteredServiceCategory userRegisteredServiceCategory);

    void deleteUserRegisteredService(Long id);

    boolean userRegisteredServiceExists(Long id);

}
