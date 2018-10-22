package com.maciejbihun.controller;

import com.maciejbihun.dto.UserUnitsRequestDto;
import com.maciejbihun.models.UserUnitsRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author BHN
 */
public interface UserUnitsRequestService {
    ResponseEntity<UserUnitsRequest> getUserUnitsRequest(Long id);
    ResponseEntity<UserUnitsRequest> saveUserUnitsRequest(UserUnitsRequestDto userUnitsRequestDto);
    ResponseEntity<UserUnitsRequest> getUserUnitsRequestByStatus(String status);
}
