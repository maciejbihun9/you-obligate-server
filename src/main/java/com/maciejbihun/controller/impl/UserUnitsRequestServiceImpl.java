package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserUnitsRequestService;
import com.maciejbihun.dto.UserUnitsRequestDto;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserUnitsRequest;
import com.maciejbihun.models.UserUnitsRequestStatus;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import com.maciejbihun.repository.UserUnitsRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author BHN
 */
@Controller
@Transactional(rollbackOn = Exception.class)
public class UserUnitsRequestServiceImpl implements UserUnitsRequestService {

    @Autowired
    UserUnitsRequestRepository userUnitsRequestRepository;

    @Autowired
    UserRegisteredServiceRepository userRegisteredServiceRepository;

    @Override
    @RequestMapping(value = "/user_units_request/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserUnitsRequest> getUserUnitsRequest(@PathVariable("id") Long id) {
        Optional<UserUnitsRequest> userUnitsRequestOptional = userUnitsRequestRepository.findById(id);
        return userUnitsRequestOptional.<ResponseEntity<UserUnitsRequest>>map(userUnitsRequest ->
                new ResponseEntity<>(userUnitsRequest, HttpStatus.FOUND)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @RequestMapping(value = "/user_units_request", method = RequestMethod.POST)
    public ResponseEntity<UserUnitsRequest> saveUserUnitsRequest(@RequestBody UserUnitsRequestDto userUnitsRequestDto) {
        Optional<UserRegisteredService> userRegisteredService = userRegisteredServiceRepository.findById(userUnitsRequestDto.getUserRegisteredServiceId());
        if (!userRegisteredService.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserUnitsRequest userUnitsRequest = new UserUnitsRequest();
        userUnitsRequest.setUserRegisteredService(userRegisteredService.get());
        userUnitsRequest.setCreatedDateTime(LocalDateTime.now());
        return new ResponseEntity<>(userUnitsRequestRepository.save(userUnitsRequest), HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/user_units_request", method = RequestMethod.GET)
    public ResponseEntity<UserUnitsRequest> getUserUnitsRequestByStatus(@RequestParam("status") String userUnitsRequestStatusString) {
        UserUnitsRequestStatus userUnitsRequestStatus = UserUnitsRequestStatus.valueOf(userUnitsRequestStatusString);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

}
