package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserRegisteredServiceController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author BHN
 */
@RestController
@Transactional(rollbackOn = Exception.class)
public class UserRegisteredServiceControllerImpl implements UserRegisteredServiceController {

    @Autowired
    UserRegisteredServiceRepository userRegisteredServiceRepository;

    @Autowired
    UserService userService;

    @Override
    @RequestMapping(value = "/user-registered-services", method = RequestMethod.POST)
    public ResponseEntity<UserRegisteredService> saveUserRegisteredService(@RequestBody UserRegisteredService userRegisteredService) {
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRegisteredService.setCreatedDateTime(LocalDateTime.now());
        User loggedUser = userService.loadUserByUsername(principal.getUsername()).getUser();
        userRegisteredService.setUser(loggedUser);
        return new ResponseEntity<>(userRegisteredServiceRepository.save(userRegisteredService), HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/user-registered-services/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserRegisteredService> getUserRegisteredService(@PathVariable("id") Long id) {
        Optional<UserRegisteredService> userRegisteredServiceOptional = userRegisteredServiceRepository.findById(id);
        return userRegisteredServiceOptional.map(userRegisteredService -> new ResponseEntity<>(userRegisteredService, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @RequestMapping(value = "/user-registered-services/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserRegisteredService(@PathVariable("id") Long id) {
        Optional<UserRegisteredService> userRegisteredServiceOptional = userRegisteredServiceRepository.findById(id);
        if (userRegisteredServiceOptional.isPresent()){
            this.userRegisteredServiceRepository.deleteById(id);
            return new ResponseEntity<>("An entity has been deleted.", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("An entity not found.", HttpStatus.NOT_FOUND);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user-registered-services", method = RequestMethod.GET)
    public ResponseEntity<List<UserRegisteredService>> getUserRegisteredServices(@RequestParam("category") String category) {
        UserRegisteredServiceCategory userRegisteredServiceCategory;
        if (category == null){
            return new ResponseEntity<>(userRegisteredServiceRepository.findAll(), HttpStatus.FOUND);
        }
        try {
            userRegisteredServiceCategory = UserRegisteredServiceCategory.valueOf(category);
            List<UserRegisteredService> byUserRegisteredServiceCategory = userRegisteredServiceRepository.findByUserRegisteredServiceCategory(userRegisteredServiceCategory);
            return new ResponseEntity<>(byUserRegisteredServiceCategory, HttpStatus.FOUND);
        } catch (IllegalArgumentException il){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

}
