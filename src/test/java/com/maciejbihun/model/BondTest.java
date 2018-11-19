package com.maciejbihun.model;

import com.maciejbihun.controller.UserGroupObligationStrategyForRegisteredServiceController;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BondTest {

    @Mock
    UserGroupObligationStrategyForRegisteredServiceController obligationStrategyController;

    @Mock
    UserRegisteredService userRegisteredService;

    @Mock
    ObligationGroup obligationGroup;

    @Mock
    UserAccountInObligationGroup userAccountInObligationGroup;

    @Test
    public void ShouldShowClosedObligationMessageWhenTryingToPayForTheBondUnitOnClosedBond(){
        UserGroupObligationStrategyForRegisteredService obligationStrategy =
                new UserGroupObligationStrategyForRegisteredService();
        obligationStrategy.setUnitOfWork(UnitOfWork.SERVICE);
        obligationStrategy.setUnitOfWorkCost(new BigDecimal("100.00"));
        obligationStrategy.setUserRegisteredService(userRegisteredService);
        obligationStrategy.setInterestRate(new BigDecimal("0.10"));
        obligationStrategy.setMinAmountOfUnitsPerBond(10);

        Integer amountOfUnitsToPay = 1;
        Bond testBond = new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay);

        assertEquals(new BigDecimal("90").multiply(new BigDecimal("12")), testBond.getAmountOfCreatedMoney());

    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowIllegalArgumentException_When_AmountOfUnitsToPayHasWrongValue(){
        UserGroupObligationStrategyForRegisteredService obligationStrategy =
                new UserGroupObligationStrategyForRegisteredService();
        obligationStrategy.setMinAmountOfUnitsPerBond(10);

        Integer amountOfUnitsToPay = 1;
        new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay);
    }

    @Test
    public void ShouldCreateConvenientAmountOfMoney(){
        UserGroupObligationStrategyForRegisteredService obligationStrategy =
                new UserGroupObligationStrategyForRegisteredService();
        obligationStrategy.setUnitOfWorkCost(new BigDecimal("100.00"));
        obligationStrategy.setInterestRate(new BigDecimal("0.10"));
        obligationStrategy.setMinAmountOfUnitsPerBond(10);
        obligationStrategy.setObligationGroup(obligationGroup);

        Integer amountOfUnitsToPay = 12;
        Bond testBond = new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay);
        assertEquals(new BigDecimal("90.00").multiply(new BigDecimal(amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_EVEN), testBond.getAmountOfCreatedMoney());

        obligationStrategy.setInterestRate(new BigDecimal("0.00"));
        testBond = new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay);
        assertEquals(new BigDecimal(amountOfUnitsToPay).multiply(obligationStrategy.getUnitOfWorkCost()), testBond.getAmountOfCreatedMoney());

        obligationStrategy.setInterestRate(new BigDecimal("-1.00"));
        testBond = new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay);
        assertEquals(new BigDecimal(amountOfUnitsToPay).multiply(obligationStrategy.getUnitOfWorkCost()), testBond.getAmountOfCreatedMoney());
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowIllegalArgumentException_When_InterestRateIsLowerThanZero(){
        UserGroupObligationStrategyForRegisteredService obligationStrategy =
                new UserGroupObligationStrategyForRegisteredService();
        obligationStrategy.setInterestRate(new BigDecimal("-0.10"));
    }

}
