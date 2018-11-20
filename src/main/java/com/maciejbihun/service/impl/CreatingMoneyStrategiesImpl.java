package com.maciejbihun.service.impl;

import com.maciejbihun.service.CreatingMoneyStrategies;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author maciej Bihun
 * Contains all creating money strategies.
 */
@Service
public class CreatingMoneyStrategiesImpl implements CreatingMoneyStrategies {

    @Override
    public BigDecimal computeAmountOfCreatedMoneyForBondWithDiscount(BigDecimal unitOfWorkCost, BigDecimal interestRate, Integer amountOfUnitsToPay) {
        unitOfWorkCost =  unitOfWorkCost.subtract(interestRate.multiply(unitOfWorkCost));
        return unitOfWorkCost.multiply(new BigDecimal(amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_UP);
    }
}
