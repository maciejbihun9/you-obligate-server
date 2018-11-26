package com.maciejbihun.service;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;

import java.math.BigDecimal;

public interface BondService {

    Bond createBondInObligationGroup(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception;

    // Bond createBond(UserGroupObligationStrategyForRegisteredService obligationStrategy, Integer amountOfUnitsToPay);

}
