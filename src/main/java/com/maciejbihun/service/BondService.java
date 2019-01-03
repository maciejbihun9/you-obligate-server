package com.maciejbihun.service;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.PurchaseToken;

import java.util.List;

public interface BondService {

    List<Bond> getObligationGroupBonds(int obligationGroupId);

    Bond createBondInObligationStrategy(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception;

    PurchaseToken reserveServiceUnit();

}
