package com.maciejbihun.controllers.impl;

import com.maciejbihun.controllers.UserRegisteredServiceController;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Override
    @RequestMapping(value = "/user-registered-services/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserRegisteredService(@PathVariable("id") Long id) {
        UserRegisteredService userRegisteredService = this.userRegisteredServiceRepository.getUserRegisteredService(id);
        if (userRegisteredService == null){
            return new ResponseEntity<>("An entity not found.", HttpStatus.NOT_FOUND);
        }
        this.userRegisteredServiceRepository.deleteUserRegisteredService(userRegisteredService);
        return new ResponseEntity<>("An entity has been deleted.", HttpStatus.NO_CONTENT);
    }

}
