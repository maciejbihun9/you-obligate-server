package com.maciejbihun.service;

import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserAccountInObligationGroup;

import java.math.BigDecimal;

public interface UserAccountInObligationGroupService {

    UserAccountInObligationGroup saveUserAccountInObligationGroup(UserAccountInObligationGroup userAccountInObligationGroup);

    UserAccountInObligationGroup getUserAccountInObligationGroupWithObligationStrategies(Long userAccountId);

    UserAccountInObligationGroup getUserAccountInObligationGroup(Long serAccountInObligationGroup);

    UserAccountInObligationGroup createUserAccountInObligationGroup(String username, Long obligationGroupId) throws ObligationGroupDoesNotExistsException;

    UserAccountInObligationGroup getUserAccountInObligationGroupForObligationGroupAndUser(Long obligationGroupId, Long userId);

    BigDecimal getUserAccountBalanceInGivenObligationGroup(Long userId, Long obligationGroupId);

    boolean userHasEnoughMoney(User user);

}
