package com.maciejbihun.service;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.PurchaseToken;

public interface BondService {

    Bond createBondInObligationStrategy(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception;

    PurchaseToken reserveServiceUnit();

}
