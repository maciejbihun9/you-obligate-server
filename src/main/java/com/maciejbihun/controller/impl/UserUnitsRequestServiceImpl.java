package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserUnitsRequestService;
import com.maciejbihun.models.UserUnitsRequest;
import com.maciejbihun.models.UserUnitsRequestStatus;
import com.maciejbihun.repository.UserUnitsRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
@Transactional(rollbackOn = Exception.class)
public class UserUnitsRequestServiceImpl implements UserUnitsRequestService {

    @Autowired
    UserUnitsRequestRepository userUnitsRequestRepository;

    @Override
    @RequestMapping(value = "/user_units_request/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserUnitsRequest> getUserUnitsRequest(@PathVariable("id") Long id) {
        Optional<UserUnitsRequest> userUnitsRequestOptional = userUnitsRequestRepository.findById(id);
        return userUnitsRequestOptional.<ResponseEntity<UserUnitsRequest>>map(userUnitsRequest ->
                new ResponseEntity<>(userUnitsRequest, HttpStatus.FOUND)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @RequestMapping(value = "/user_units_request", method = RequestMethod.POST)
    public ResponseEntity<UserUnitsRequest> saveUserUnitsRequest(@RequestBody UserUnitsRequest userUnitsRequest) {
        userUnitsRequest = userUnitsRequestRepository.save(userUnitsRequest);
        return new ResponseEntity<>(userUnitsRequest, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/user_units_request", method = RequestMethod.GET)
    public ResponseEntity<UserUnitsRequest> getUserUnitsRequestByStatus(@RequestParam("status") String userUnitsRequestStatusString) {
        UserUnitsRequestStatus userUnitsRequestStatus = UserUnitsRequestStatus.valueOf(userUnitsRequestStatusString);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

}
