package com.maciejbihun.model;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BondTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAmountOfUnitsToPayHasWrongValue(){
        // given
        RegisteredServiceObligationStrategy registeredServiceObligationStrategyMock = Mockito.mock(RegisteredServiceObligationStrategy.class);
        Mockito.when(registeredServiceObligationStrategyMock.getMinAmountOfUnitsPerBond()).thenReturn(10);
        Integer amountOfUnitsToPay = 1;

        // then
        new Bond(registeredServiceObligationStrategyMock, amountOfUnitsToPay);
    }

    @Test
    public void shouldReturnCorrectAmountOfCreatedMoney(){
        // given
        RegisteredServiceObligationStrategy registeredServiceObligationStrategy = new RegisteredServiceObligationStrategy(
                Mockito.mock(UserRegisteredService.class),
                Mockito.mock(UserAccountInObligationGroup.class),
                UnitOfWork.SERVICE,
                BigDecimal.valueOf(10000, 2),
                BigDecimal.valueOf(5, 2),
                2,
                20
        );
        Integer amountOfUnitsToPay = 10;

        // when
        Bond bond = new Bond(registeredServiceObligationStrategy, amountOfUnitsToPay);

        // then
        assertEquals(BigDecimal.valueOf(95000, 2), bond.getAmountOfCreatedMoney());
    }

}
