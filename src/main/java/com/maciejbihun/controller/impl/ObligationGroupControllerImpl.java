package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupController;
import com.maciejbihun.converters.ObligationGroupConverter;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.repository.ObligationGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * @author Maciej Bihun
 */
@Controller
public class ObligationGroupControllerImpl implements ObligationGroupController {

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Autowired
    private com.maciejbihun.service.ObligationGroupService obligationGroupService;

    @Override
    public ResponseEntity<ObligationGroupDto> saveObligationGroup(ObligationGroupDto obligationGroupDto) {
        ObligationGroup obligationGroup = ObligationGroupConverter.convertToEntity(obligationGroupDto);
        obligationGroup = obligationGroupService.saveObligationGroup(obligationGroup);
        return new ResponseEntity<>(ObligationGroupConverter.convertToDto(obligationGroup), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ObligationGroup> loadObligationGroupById(Long id) {
        return this.obligationGroupRepository.findById(id).map(obligationGroup ->
                new ResponseEntity<>(obligationGroup, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
