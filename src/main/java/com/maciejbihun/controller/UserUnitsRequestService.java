package com.maciejbihun.controller;

import com.maciejbihun.models.UserUnitsRequest;
import org.springframework.http.ResponseEntity;

public interface UserUnitsRequestService {
    ResponseEntity<UserUnitsRequest> getUserUnitsRequest(Long id);
    ResponseEntity<UserUnitsRequest> saveUserUnitsRequest(UserUnitsRequest userUnitsRequest);
    ResponseEntity<UserUnitsRequest> getUserUnitsRequestByStatus(String status);
}
