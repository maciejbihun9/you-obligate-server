package com.maciejbihun.service.impl;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ServiceTag;
import com.maciejbihun.models.User;
import com.maciejbihun.repository.ObligationGroupRepository;
import com.maciejbihun.service.ObligationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class ObligationGroupServiceImpl implements ObligationGroupService {

    @Autowired
    private EntityManager entityManager;

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
     */
    @Override
    public boolean obligationGroupExists(Long obligationGroupId) {
        return obligationGroupRepository.existsById(obligationGroupId);
    }

    /**
     * Returns Obligation group with all registered services in given group tags.
     */
    @Override
    public ObligationGroup getObligationGroupWithRegisteredServicesTags(Long obligationGroupId) {
        List<ObligationGroup> obligationGroups = entityManager.createQuery(
                "select o " +
                        "from ObligationGroup o " +
                        "join fetch o.userAccountsInObligationGroup u " +
                        "join fetch u.userObligationStrategies uo " +
                        "join fetch uo.userRegisteredService ur " +
                        "join fetch ur.userRegisteredServiceTags urt "
                , ObligationGroup.class).getResultList();
        // HashMap<String, Object> properties = new HashMap<>();
        // properties.put("javax.persistence.fetchgraph", graph);
        // return entityManager.find(ObligationGroup.class, obligationGroupId, properties);
        return obligationGroups.get(0);
    }

    /**
     * Recommends obligation groups for a user.
     */
    @Override
    public List<ObligationGroup> recommendObligationGroupsForUser(User user) {
        Set<ServiceTag> expectedServicesTags = user.getExpectedServicesTags();



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

    /**
     * Returns Obligation groups that contains registered services with given registered services tags.
     */
    @Override
    public List<ObligationGroup> getObligationGroupsWithGivenRegisteredServicesTags(Set<ServiceTag> registeredServicesTags) {
        // get all obligation groups with registered services tags in one query
        // check how many item matches
        // return top couple of them
        return null;
    }

}
