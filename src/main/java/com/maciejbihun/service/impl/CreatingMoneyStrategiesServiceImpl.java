package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.BondWithDiscountCanNotHaveNegativeInterestRateException;
import com.maciejbihun.exceptions.NegativeValueException;
import com.maciejbihun.service.CreatingMoneyStrategiesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author maciej Bihun
 * Contains all creating money strategies.
 */
@Service
public class CreatingMoneyStrategiesServiceImpl implements CreatingMoneyStrategiesService {

    /**
     * Computes the amount of money created after accepting a bond with discount in the obligation group.
     * Values passed to that method can not be negative.
     */
    @Override
    public BigDecimal computeAmountOfCreatedMoneyForBondWithDiscount(BigDecimal unitOfWorkCost, BigDecimal interestRate, Integer amountOfUnitsToPay) {
        if (unitOfWorkCost.compareTo(BigDecimal.ZERO) < 0 || interestRate.compareTo(BigDecimal.ZERO) < 0 || amountOfUnitsToPay < 0){
            throw new NegativeValueException();
        }
        unitOfWorkCost =  unitOfWorkCost.subtract(interestRate.multiply(unitOfWorkCost));
        return unitOfWorkCost.multiply(new BigDecimal(amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_UP);
    }
}
