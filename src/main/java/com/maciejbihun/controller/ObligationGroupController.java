package com.maciejbihun.controller;

import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.models.ObligationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

public interface ObligationGroupController {

    ResponseEntity<ObligationGroupDto> saveObligationGroup(ObligationGroupDto obligationGroupDto);
    ResponseEntity<ObligationGroup> loadObligationGroupById(Long id);

}
