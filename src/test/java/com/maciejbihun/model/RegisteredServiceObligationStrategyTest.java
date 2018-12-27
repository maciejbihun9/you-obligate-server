package com.maciejbihun.model;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.exceptions.NegativeValueException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserRegisteredService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class RegisteredServiceObligationStrategyTest {

    @Test(expected = NegativeValueException.class)
    public void shouldThrowNegativeValueExceptionWhenUnitOfWorkCostIsLowerThanZero(){
        RegisteredServiceObligationStrategy registeredServiceObligationStrategy = new RegisteredServiceObligationStrategy(
                Mockito.mock(UserRegisteredService.class),
                Mockito.mock(UserAccountInObligationGroup.class),
                UnitOfWork.SERVICE,
                BigDecimal.valueOf(-10000, 2),
                BigDecimal.valueOf(5, 2),
                2,
                10
        );
        Integer amountOfUnitsToPay = 11;
        //new Bond(registeredServiceObligationStrategy, amountOfUnitsToPay);
    }

}
