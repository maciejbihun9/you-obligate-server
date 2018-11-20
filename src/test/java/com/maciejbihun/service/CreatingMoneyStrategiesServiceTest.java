package com.maciejbihun.service;

import com.maciejbihun.exceptions.BondWithDiscountCanNotHaveNegativeInterestRateException;
import com.maciejbihun.service.impl.CreatingMoneyStrategiesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreatingMoneyStrategiesServiceTest {

    private CreatingMoneyStrategiesService creatingMoneyStrategiesService;

    @Before
    public void init(){
        creatingMoneyStrategiesService = new CreatingMoneyStrategiesServiceImpl();
    }

    @Test
    public void shouldComputeAmountOfCreatedMoneyForBondWithDiscount(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(5, 2);
        Integer amountOfUnitsToPay = 100;
        // when
        BigDecimal createdMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
        // then
        assertEquals(BigDecimal.valueOf(950000, 2), createdMoney);


        // given
        interestRate = BigDecimal.valueOf(0, 2);
        // when
        createdMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
        // then
        assertEquals(BigDecimal.valueOf(1000000, 2), createdMoney);
    }

    @Test(expected = BondWithDiscountCanNotHaveNegativeInterestRateException.class)
    public void shouldThrowExceptionIfInterestRateIsLowerThanZero(){
        // given
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        BigDecimal interestRate = BigDecimal.valueOf(-1, 1);
        Integer amountOfUnitsToPay = 100;
        // when
        BigDecimal createdMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(unitOfWorkCost, interestRate, amountOfUnitsToPay);
        // then
        assertEquals(BigDecimal.valueOf(950000, 2), createdMoney);
    }

}
