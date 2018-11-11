package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupService;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.repository.ObligationGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ObligationGroupServiceImpl implements ObligationGroupService {

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Override
    public ObligationGroup saveObligationGroup(ObligationGroup obligationGroup) {
        return this.obligationGroupRepository.save(obligationGroup);
    }

    @Override
    public ResponseEntity<ObligationGroup> loadObligationGroupById(Long id) {
        return this.obligationGroupRepository.findById(id).map(obligationGroup ->
                new ResponseEntity<>(obligationGroup, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
