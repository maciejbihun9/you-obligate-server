package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.BondWithDiscountCanNotHaveNegativeInterestRateException;
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

    @Override
    public BigDecimal computeAmountOfCreatedMoneyForBondWithDiscount(BigDecimal unitOfWorkCost, BigDecimal interestRate, Integer amountOfUnitsToPay) {
        if (interestRate.compareTo(BigDecimal.ZERO) < 0){
            throw new BondWithDiscountCanNotHaveNegativeInterestRateException();
        }
        unitOfWorkCost =  unitOfWorkCost.subtract(interestRate.multiply(unitOfWorkCost));
        return unitOfWorkCost.multiply(new BigDecimal(amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_UP);
    }
}
