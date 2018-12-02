package com.maciejbihun.service;

import com.maciejbihun.exceptions.NegativeValueException;
import com.maciejbihun.service.impl.CreatingMoneyStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreatingMoneyStrategiesServiceTest {

    /*private CreatingMoneyStrategiesService creatingMoneyStrategiesService;

    @Before
    public void init(){
        creatingMoneyStrategiesService = new CreatingMoneyStrategy();
    }

    @Test
    public void shouldComputeAmountOfCreatedMoneyForBondWithDiscount(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(50, 4);
        Integer amountOfUnitsToPay = 100;
        // when
        BigDecimal createdMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
        // then
        assertEquals(BigDecimal.valueOf(995000, 2), createdMoney);


        // given
        interestRate = BigDecimal.valueOf(0, 2);
        // when
        createdMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
        // then
        assertEquals(BigDecimal.valueOf(1000000, 2), createdMoney);


        // given
        unitOfWorkCost = BigDecimal.valueOf(0, 0);
        interestRate = BigDecimal.valueOf(0, 0);
        amountOfUnitsToPay = 0;
        // when
        createdMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
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
        creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
    }

    @Test(expected = NegativeValueException.class)
    public void shouldThrowExceptionIfPassedInterestRateIsLowerThanZero(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(-1, 1);
        Integer amountOfUnitsToPay = 100;
        // then
        creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
    }

    @Test(expected = NegativeValueException.class)
    public void shouldThrowExceptionIfPassedAmountOfUnitsToPayIsLowerThanZero(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(5, 2);
        Integer amountOfUnitsToPay = -100;
        // then
        creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
    }
*/
}
