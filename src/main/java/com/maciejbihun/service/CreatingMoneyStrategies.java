package com.maciejbihun.service;

import java.math.BigDecimal;

public interface CreatingMoneyStrategies {

    BigDecimal computeAmountOfCreatedMoneyForBondWithDiscount(BigDecimal unitOfWorkCost, BigDecimal interestRate, Integer amountOfUnitsToPay);

}
