package com.maciejbihun.service.impl;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import com.maciejbihun.repository.ObligationGroupRepository;
import com.maciejbihun.service.ObligationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<String> getObligationGroupRegisteredServicesTerms(Long obligationGroupId) {
        List<String> obligationGroupRegisteredServicesTerms = new ArrayList<>();
        Optional<ObligationGroup> obligationGroupById = obligationGroupRepository.findById(obligationGroupId);
        ObligationGroup obligationGroup = obligationGroupById.get();
        obligationGroup.getUserAccountsInObligationGroup().forEach(userAccountInObligationGroup -> {
            userAccountInObligationGroup.getUserObligationStrategies().forEach(registeredServiceObligationStrategy -> {
                List<String> registeredServiceTerms = registeredServiceObligationStrategy.getUserRegisteredService().getRegisteredServiceTerms();
                obligationGroupRegisteredServicesTerms.addAll(registeredServiceTerms);
            });
        });
        return obligationGroupRegisteredServicesTerms;
    }

    /**
     * Recommends ObligationGroups for given user taking into account all services terms.
     */
    @Override
    public List<ObligationGroup> recommendObligationGroupsForUser(User user) {
        List<String> expectedServicesTerms = user.getExpectedServicesTerms();
        // try to develop that searching using java
        // get all groups with users registered services terms,
        // iterate through all groups and count how many items matches expectedServicesTerms

        // final result - Map<ObligationGroup, Integer (numberOfSimilarItems)>
        List<ObligationGroup> obligationGroups = obligationGroupRepository.findAll();
        obligationGroups.forEach(obligationGroup -> {
            obligationGroup.getUserAccountsInObligationGroup();
        });

        return null;
    }

}
