package com.maciejbihun.service;

import java.math.BigDecimal;

public interface CreatingMoneyStrategiesService {

    BigDecimal computeAmountOfCreatedMoneyForBondWithDiscount(BigDecimal unitOfWorkCost, BigDecimal interestRate, Integer amountOfUnitsToPay);

}
