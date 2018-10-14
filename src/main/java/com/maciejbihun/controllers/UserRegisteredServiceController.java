package com.maciejbihun.controllers;

import com.maciejbihun.models.UserRegisteredService;
import java.util.List;

/**
 * @author BHN
 */
public interface UserRegisteredServiceController {

    UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService);

    UserRegisteredService getUserRegisteredService(Long id);

    List<UserRegisteredService> getAllUserRegisteredServices();

}
