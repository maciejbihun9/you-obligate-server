package com.maciejbihun.controllers;

import com.maciejbihun.models.UserUnitsRequest;
import org.springframework.http.ResponseEntity;

public interface UserUnitsRequestService {
    ResponseEntity<UserUnitsRequest> getUserUnitsRequest(Long id);
    ResponseEntity<UserUnitsRequest> saveUserUnitsRequest(UserUnitsRequest userUnitsRequest);
}
