package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.NegativeValueException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Contains creating money strategies.
 * @author Maciej Bihun
 *
 * TESTED
 *
 */
public class CreatingMoneyStrategy {

    /**
     * Computes the amount of money created after accepting a bond with discount in the obligation group.
     * Values passed to that method can not be negative.
     */
    public static BigDecimal amountOfCreatedMoney(BigDecimal unitOfWorkCost, BigDecimal interestRate, Integer amountOfUnitsToServe) {
        if (unitOfWorkCost.compareTo(BigDecimal.ZERO) < 0 || interestRate.compareTo(BigDecimal.ZERO) < 0 || amountOfUnitsToServe < 0){
            throw new NegativeValueException();
        }
        unitOfWorkCost =  unitOfWorkCost.subtract(interestRate.multiply(unitOfWorkCost));
        return unitOfWorkCost.multiply(new BigDecimal(amountOfUnitsToServe)).setScale(2, RoundingMode.HALF_UP);
    }
}
