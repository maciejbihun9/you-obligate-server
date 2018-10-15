package com.maciejbihun.controllers.impl;

import com.maciejbihun.controllers.UserRegisteredServiceController;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional(rollbackOn = Exception.class)
public class UserRegisteredServiceControllerImpl implements UserRegisteredServiceController {

    @Autowired
    UserRegisteredServiceRepository userRegisteredServiceRepository;

    @Override
    @RequestMapping(value = "/user-registered-services", method = RequestMethod.POST)
    public UserRegisteredService saveUserRegisteredService(@RequestBody UserRegisteredService userRegisteredService) {
        return userRegisteredServiceRepository.saveUserRegisteredService(userRegisteredService);
    }

    @Override
    public UserRegisteredService getUserRegisteredService(Long id) {
        return userRegisteredServiceRepository.getUserRegisteredService(id);
    }

    @Override
    @RequestMapping(value = "/user-registered-services", method = RequestMethod.GET)
    public List<UserRegisteredService> getAllUserRegisteredServices() {
        return userRegisteredServiceRepository.getAllUserRegisteredServices();
    }


}
