package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupService;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.repository.ObligationGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ObligationGroupServiceImpl implements ObligationGroupService {

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Override
    public ObligationGroup saveObligationGroup(ObligationGroup obligationGroup) {
        return this.obligationGroupRepository.save(obligationGroup);
    }
}
