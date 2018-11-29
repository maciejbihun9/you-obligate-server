package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserController;
import com.maciejbihun.controller.UserRegisteredServiceController;
import com.maciejbihun.converters.UserRegisteredServiceConverter;
import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import com.maciejbihun.service.UserRegisteredServiceService;
import com.maciejbihun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Controller
public class UserRegisteredServiceControllerImpl implements UserRegisteredServiceController {

    @Autowired
    private UserRegisteredServiceService userRegisteredServiceService;

    @Autowired
    private UserService userService;

    // TODO - IMPLEMENT EXCEPTION HANDLING
    @Override
    @RequestMapping(value = "/user-registered-services", method = RequestMethod.POST)
    public ResponseEntity<UserRegisteredServiceDto> saveUserRegisteredService(@RequestBody UserRegisteredServiceDto userRegisteredServiceDto) {

        UserPrincipal userPrincipal = userService.loadUserByUsername(userRegisteredServiceDto.getUserDto().getUsername());

        UserRegisteredService userRegisteredService = UserRegisteredServiceConverter.convertToEntity(userRegisteredServiceDto);
        userRegisteredService.setUser(userPrincipal.getUser());

        userRegisteredService = userRegisteredServiceService.saveUserRegisteredService(userRegisteredService);

        return new ResponseEntity<>(UserRegisteredServiceConverter.convertToDto(userRegisteredService), HttpStatus.CREATED);
    }

    // TODO - IMPLEMENT EXCEPTION HANDLING
    @Override
    @RequestMapping(value = "/user-registered-services/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserRegisteredServiceDto> getUserRegisteredService(@PathVariable("id") Long id) {
        Optional<UserRegisteredService> optionalUserRegisteredService = userRegisteredServiceService.getUserRegisteredService(id);
        return optionalUserRegisteredService.map(userRegisteredService ->
                new ResponseEntity<>(UserRegisteredServiceConverter.convertToDto(userRegisteredService), HttpStatus.FOUND)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // TODO - IMPLEMENT EXCEPTION HANDLING
    @Override
    @RequestMapping(value = "/user-registered-services/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserRegisteredService(@PathVariable("id") Long id) {
        if (userRegisteredServiceService.userRegisteredServiceExists(id)){
            userRegisteredServiceService.deleteUserRegisteredService(id);
            return new ResponseEntity<>("An entity has been deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("An entity was not found.", HttpStatus.NOT_FOUND);
    }

    // TODO - IMPLEMENT EXCEPTION HANDLING
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user-registered-services", method = RequestMethod.GET)
    public ResponseEntity<List<UserRegisteredServiceDto>> getUserRegisteredServices(@RequestParam("category") String category) {
        UserRegisteredServiceCategory userRegisteredServiceCategory;
        if (category == null){
            List<UserRegisteredServiceDto> userRegisteredServiceDtos = new ArrayList<>();
            userRegisteredServiceService.getUserRegisteredServices().forEach(userRegisteredService -> {
                userRegisteredServiceDtos.add(UserRegisteredServiceConverter.convertToDto(userRegisteredService));
            });
            return new ResponseEntity<>(userRegisteredServiceDtos, HttpStatus.OK);
        }
        try {
            userRegisteredServiceCategory = UserRegisteredServiceCategory.valueOf(category);
            List<UserRegisteredService> byUserRegisteredServiceCategory = userRegisteredServiceService.getUserRegisteredServicesByCategory(userRegisteredServiceCategory);
            List<UserRegisteredServiceDto> userRegisteredServiceDtos = new ArrayList<>();
            byUserRegisteredServiceCategory.forEach(userRegisteredService -> {
                userRegisteredServiceDtos.add(UserRegisteredServiceConverter.convertToDto(userRegisteredService));
            });
            return new ResponseEntity<>(userRegisteredServiceDtos, HttpStatus.FOUND);
        } catch (IllegalArgumentException il){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

}
