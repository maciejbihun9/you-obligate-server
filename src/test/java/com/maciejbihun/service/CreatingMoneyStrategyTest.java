package com.maciejbihun.service;

import com.maciejbihun.exceptions.NegativeValueException;
import com.maciejbihun.service.impl.CreatingMoneyStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreatingMoneyStrategyTest {

    @Test
    public void shouldComputeAmountOfCreatedMoneyForBondWithDiscount(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(5, 3);
        int amountOfUnitsToServe = 100;
        // when
        BigDecimal createdMoney = CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost, interestRate, amountOfUnitsToServe);
        // then
        assertEquals(BigDecimal.valueOf(995000, 2), createdMoney);


        // given
        interestRate = BigDecimal.valueOf(0, 2);
        // when
        createdMoney = CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost, interestRate, amountOfUnitsToServe);
        // then
        assertEquals(BigDecimal.valueOf(1000000, 2), createdMoney);


        // given
        unitOfWorkCost = BigDecimal.valueOf(0, 0);
        interestRate = BigDecimal.valueOf(0, 0);
        amountOfUnitsToServe = 0;
        // when
        createdMoney = CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost, interestRate, amountOfUnitsToServe);
        // then
        assertEquals(BigDecimal.valueOf(0, 2), createdMoney);
    }

    @Test(expected = NegativeValueException.class)
    public void shouldThrowExceptionIfPassedUnitOfWorkIsLowerThanZero(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(-10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(5, 2);
        Integer amountOfUnitsToPay = 100;
        // then
        CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost, interestRate, amountOfUnitsToPay);
    }

    @Test(expected = NegativeValueException.class)
    public void shouldThrowExceptionIfPassedInterestRateIsLowerThanZero(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(-1, 1);
        Integer amountOfUnitsToPay = 100;
        // then
        CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost, interestRate, amountOfUnitsToPay);
    }

    @Test(expected = NegativeValueException.class)
    public void shouldThrowExceptionIfPassedAmountOfUnitsToPayIsLowerThanZero(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(5, 2);
        Integer amountOfUnitsToPay = -100;
        // then
        CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost, interestRate, amountOfUnitsToPay);
    }

}
