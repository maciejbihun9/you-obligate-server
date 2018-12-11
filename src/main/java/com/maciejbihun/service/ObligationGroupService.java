package com.maciejbihun.service;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ServiceTag;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredService;

import java.util.List;
import java.util.Set;

public interface ObligationGroupService {

    ObligationGroup saveObligationGroup(ObligationGroup obligationGroup);

    List<ObligationGroup> getObligationGroupsWithRegisteredServicesTags();

    boolean obligationGroupExists(Long obligationGroupId);

    List<ObligationGroup> getObligationGroupsByServicesTags(Set<ServiceTag> servicesTags);

    List<ObligationGroup> getRecommendObligationGroups(Set<ServiceTag> userExpectedServicesTags,
                                                       List<ObligationGroup> obligationGroupsWithServicesTags);

    List<ObligationGroup> getObligationGroupsWithTheLargestAmountOfRegisteredServices();

    List<ObligationGroup> getObligationGroupsWithGivenRegisteredServicesTags(Set<ServiceTag> registeredServicesTags);

    List<UserRegisteredService> getObligationGroupWithRegisteredServices(Long obligationGroupId);

}
