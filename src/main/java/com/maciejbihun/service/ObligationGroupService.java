package com.maciejbihun.service;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ServiceTag;
import com.maciejbihun.models.User;

import java.util.List;
import java.util.Set;

public interface ObligationGroupService {

    ObligationGroup saveObligationGroup(ObligationGroup obligationGroup);

    ObligationGroup getObligationGroupWithRegisteredServicesTags(Long obligationGroupId);

    boolean obligationGroupExists(Long obligationGroupId);

    List<ObligationGroup> recommendObligationGroupsForUser(User user);

    List<ObligationGroup> getObligationGroupsWithGivenRegisteredServicesTags(Set<ServiceTag> registeredServicesTags);

}
