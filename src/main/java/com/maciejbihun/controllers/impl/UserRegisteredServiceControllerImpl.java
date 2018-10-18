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
    @RequestMapping(value = "/user-registered-services/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserRegisteredService> getUserRegisteredService(@PathVariable("id") Long id) {
        UserRegisteredService userRegisteredService = userRegisteredServiceRepository.getUserRegisteredService(id);
        if (userRegisteredService == null){
            return new ResponseEntity<>(userRegisteredService, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userRegisteredServiceRepository.getUserRegisteredService(id), HttpStatus.OK);
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
