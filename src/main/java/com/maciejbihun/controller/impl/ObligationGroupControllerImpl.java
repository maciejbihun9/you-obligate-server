package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupController;
import com.maciejbihun.converters.ObligationGroupConverter;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.repository.ObligationGroupRepository;
import com.maciejbihun.service.ObligationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maciej Bihun
 */
@RestController
public class ObligationGroupControllerImpl implements ObligationGroupController {

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Autowired
    private ObligationGroupService obligationGroupService;

    @Override
    @GetMapping("/obligation-groups")
    public ResponseEntity<List<ObligationGroupDto>> getObligationGroups() {
        List<ObligationGroupDto> obligationGroupDtos = new ArrayList<>();
        List<ObligationGroup> obligationGroups = obligationGroupService.getObligationGroups();
        obligationGroups.forEach(obligationGroup -> {
            obligationGroupDtos.add(ObligationGroupConverter.convertToDto(obligationGroup));
        });
        return new ResponseEntity<>(obligationGroupDtos, HttpStatus.FOUND);
    }

    @Override
    @GetMapping("/obligation-groups/{obligationGroupId}/bonds")
    public ResponseEntity<ObligationGroupDto> getObligationGroupWithBonds(@PathVariable int obligationGroupId) {
        return null;
    }

    @Override
    @GetMapping("/obligation-groups-with-bonds")
    public ResponseEntity<List<ObligationGroupDto>> getObligationGroupsWithBonds(@RequestParam(value = "obligationGroupsIds") String[] obligationGroupsIds) {
        List<ObligationGroupDto> obligationGroupDtos = new ArrayList<>();
        List<ObligationGroup> obligationGroups = obligationGroupService.getObligationGroupsWithBonds(obligationGroupsIds);
        obligationGroups.forEach(obligationGroup -> {
            obligationGroupDtos.add(ObligationGroupConverter.convertToDto(obligationGroup));
        });
        return new ResponseEntity<>(obligationGroupDtos, HttpStatus.FOUND);

    }

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
