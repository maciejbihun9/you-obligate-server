package com.maciejbihun.service;

import com.maciejbihun.models.Bond;

public interface BondService {

    Bond createBondInObligationStrategy(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception;

    Integer subtractObligationUnitFromBond(Long bondId);

}
