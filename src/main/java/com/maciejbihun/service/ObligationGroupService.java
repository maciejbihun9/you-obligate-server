package com.maciejbihun.service;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ServiceTag;
import java.util.List;
import java.util.Set;

/**
 * @author Maciej Bihun
 */
public interface ObligationGroupService {

    ObligationGroup saveObligationGroup(ObligationGroup obligationGroup);

    List<ObligationGroup> getObligationGroupsWithRegisteredServicesTags();

    boolean obligationGroupExists(Long obligationGroupId);

    List<ObligationGroup> getObligationGroupsByServicesTags(Set<ServiceTag> servicesTags);

    List<ObligationGroup> getRecommendObligationGroups(Set<ServiceTag> userExpectedServicesTags,
                                                       List<ObligationGroup> obligationGroupsWithServicesTags);

    List<ObligationGroup> getObligationGroupsWithTheLargestAmountOfRegisteredServices();

    List<ObligationGroup> getObligationGroupsWithGivenRegisteredServicesTags(Set<ServiceTag> registeredServicesTags);

    ObligationGroup getObligationGroupWithRegisteredServices(Long obligationGroupId);



}
