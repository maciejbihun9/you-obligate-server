package com.maciejbihun.service.impl;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.repository.ObligationGroupRepository;
import com.maciejbihun.service.ObligationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class ObligationGroupServiceImpl implements ObligationGroupService {

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    /**
     * Saves and updates given obligation group.
     */
    @Override
    public ObligationGroup saveObligationGroup(ObligationGroup obligationGroup) {
        return obligationGroupRepository.save(obligationGroup);
    }

    /**
     * Checks if obligation group with given id exists.
     * @param obligationGroupId
     * @return
     */
    @Override
    public boolean obligationGroupExists(Long obligationGroupId) {
        return obligationGroupRepository.existsById(obligationGroupId);
    }

}
