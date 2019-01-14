package com.maciejbihun.service;

import com.maciejbihun.models.Bond;

import java.util.List;
import java.util.Optional;

public interface BondService {

    List<Bond> getObligationGroupBonds(int obligationGroupId);

    Bond createBondInObligationStrategy(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception;

    Optional<Bond> getBond(int bondId);



}
