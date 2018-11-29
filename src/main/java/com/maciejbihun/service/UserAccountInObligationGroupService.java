package com.maciejbihun.service;

import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserAccountInObligationGroup;

public interface UserAccountInObligationGroupService {

    UserAccountInObligationGroup getUserAccountInObligationGroupWithObligationStrategies(Long userAccountId);

    UserAccountInObligationGroup getUserAccountInObligationGroup(Long accountId);

    UserAccountInObligationGroup createUserAccountInObligationGroup(String username, Long obligationGroupId) throws ObligationGroupDoesNotExistsException;



}
