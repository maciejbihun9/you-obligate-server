package com.maciejbihun.service;

import com.maciejbihun.models.ObligationGroup;

public interface ObligationGroupService {

    ObligationGroup createObligationGroup(ObligationGroup obligationGroup);

    boolean obligationGroupExists(Long obligationGroupId);

}
