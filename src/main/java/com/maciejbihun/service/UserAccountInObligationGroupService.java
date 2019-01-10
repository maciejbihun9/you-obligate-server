package com.maciejbihun.service;

import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserAccountInObligationGroup;

import java.math.BigDecimal;

public interface UserAccountInObligationGroupService {

    UserAccountInObligationGroup getUserAccountInObligationGroupWithObligationStrategies(Long userAccountId);

    UserAccountInObligationGroup getUserAccountInObligationGroup(Long accountId);

    UserAccountInObligationGroup createUserAccountInObligationGroup(String username, Long obligationGroupId) throws ObligationGroupDoesNotExistsException;

    UserAccountInObligationGroup getUserAccountInObligationGroupByObligationGroupId(Long obligationGroupId);

    BigDecimal getUserAccountBalanceInGivenObligationGroup(Long userId, Long obligationGroupId);

    boolean userHasEnoughMoney(User user);

}
