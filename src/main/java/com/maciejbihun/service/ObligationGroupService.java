package com.maciejbihun.service;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;

import java.util.List;

public interface ObligationGroupService {

    ObligationGroup saveObligationGroup(ObligationGroup obligationGroup);

    ObligationGroup getObligationGroupWithRegisteredServicesTags(Long obligationGroupId);

    boolean obligationGroupExists(Long obligationGroupId);

    List<ObligationGroup> recommendObligationGroupsForUser(User user);

}
