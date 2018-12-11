package com.maciejbihun.service.impl;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ServiceTag;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredService;
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
     * Returns Obligation groups with all registered services tags fetched.
     */
    @Override
    public List<ObligationGroup> getObligationGroupsWithRegisteredServicesTags() {
        return obligationGroupRepository.getObligationGroupsWithRegisteredServicesTags();
    }

    /**
     * Returns obligation groups that are recommended for given user taking into account his expected services.
     */
    @Override
    public List<ObligationGroup> getRecommendObligationGroups(final Set<ServiceTag> userExpectedServicesTags,
                                                              final List<ObligationGroup> obligationGroupsWithServicesTags) {
         // use the copy constructor
        Map<ObligationGroup, Integer> numberOfCommonTagsInObligationGroup = new TreeMap<>();
        obligationGroupsWithServicesTags.forEach(obligationGroup -> {
            Set<ServiceTag> commonServicesTags = new HashSet<>(userExpectedServicesTags);
            obligationGroup.getUserAccountsInObligationGroup().forEach(userAccountInObligationGroup -> {
                userAccountInObligationGroup.getUserObligationStrategies().forEach(registeredServiceObligationStrategy -> {
                    Set<ServiceTag> userRegisteredServiceTags =
                            registeredServiceObligationStrategy.getUserRegisteredService().getUserRegisteredServiceTags();
                    commonServicesTags.retainAll(userRegisteredServiceTags);
                    numberOfCommonTagsInObligationGroup.put(obligationGroup, commonServicesTags.size());
                });
            });
        });

        List<Map.Entry<ObligationGroup, Integer>> list = new ArrayList<>(numberOfCommonTagsInObligationGroup.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        List<ObligationGroup> recommendedObligationGroups = new ArrayList<>();
        for (Map.Entry<ObligationGroup, Integer> entry : list) {
            recommendedObligationGroups.add(entry.getKey());
        }
        return recommendedObligationGroups;
    }

    /**
     * Returns obligation groups sorted by the amount of registered services.
     * This number is much need to
     */
    @Override
    public List<ObligationGroup> getObligationGroupsWithTheLargestAmountOfRegisteredServices() {
        return null;
    }

    /**
     * Returns obligation groups sorted by provided servicesTags.
     */
    @Override
    public List<ObligationGroup> getObligationGroupsByServicesTags(Set<ServiceTag> servicesTags) {
        return getRecommendObligationGroups(servicesTags, getObligationGroupsWithRegisteredServicesTags());
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

    @Override
    public List<UserRegisteredService> getObligationGroupWithRegisteredServices(Long obligationGroupId) {
        return null;
    }

}
