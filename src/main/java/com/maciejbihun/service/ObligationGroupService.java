package com.maciejbihun.service;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;

import java.util.List;

public interface ObligationGroupService {

    ObligationGroup saveObligationGroup(ObligationGroup obligationGroup);

    boolean obligationGroupExists(Long obligationGroupId);

    List<String> getObligationGroupRegisteredServicesTerms(Long obligationGroupId);

    List<ObligationGroup> recommendObligationGroupsForUser(User user);

}
