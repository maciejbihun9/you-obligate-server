package com.maciejbihun.controller;

import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.models.ObligationGroup;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ObligationGroupController {

    ResponseEntity<List<ObligationGroupDto>> getObligationGroups();
    ResponseEntity<ObligationGroupDto> saveObligationGroup(ObligationGroupDto obligationGroupDto);
    ResponseEntity<ObligationGroup> loadObligationGroupById(Long id);

}
