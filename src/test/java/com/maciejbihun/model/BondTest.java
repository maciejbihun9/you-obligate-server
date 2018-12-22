package com.maciejbihun.model;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author Maciej Bihun
 */
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
        assertEquals(BigDecimal.valueOf(95000, 2), bond.getAvailableBalance());
    }

    @Test
    public void subtractBondUnit(){
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
        bond.subtractBondUnit();

        //then
        assertEquals(amountOfUnitsToPay, bond.getAmountOfServiceUnitsToBeDelivered());
    }

    @Test
    public void reserveUnit(){
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

        //then
        bond.reserveUnit();

        // then
        assertEquals(9, bond.getAmountOfServiceUnitsToBeDelivered().intValue());
        assertEquals(1, bond.getReservedUnits().intValue());
        assertEquals(BigDecimal.valueOf(9500, 2), bond.getBlockedBalance());
        assertEquals(BigDecimal.valueOf(85500, 2), bond.getAvailableBalance());
    }

    @Test
    public void reservingUnitsShouldWorkWellInMultithreadedEnvironment() throws InterruptedException {
        // given
        RegisteredServiceObligationStrategy registeredServiceObligationStrategy = new RegisteredServiceObligationStrategy(
                Mockito.mock(UserRegisteredService.class),
                Mockito.mock(UserAccountInObligationGroup.class),
                UnitOfWork.SERVICE,
                BigDecimal.valueOf(10000, 2),
                BigDecimal.valueOf(0, 2),
                2,
                1000
        );
        Integer amountOfUnitsToPay = 1000;

        // when
        Bond bond = new Bond(registeredServiceObligationStrategy, amountOfUnitsToPay);

        // create couple threads
        Thread r = new Thread(()->{
            int i = 0;
            while(i < 300){
                bond.reserveUnit();
                i++;
            }
        });
        Thread r1 = new Thread(()->{
            int i = 0;
            while(i < 300){
                bond.reserveUnit();
                i++;
            }
        });
        Thread r2 = new Thread(()->{
            int i = 0;
            while(i < 300){
                bond.reserveUnit();
                i++;
            }
        });

        r.start();
        r1.start();
        r2.start();

        r.join();
        r1.join();
        r2.join();

        //then
        assertEquals(BigDecimal.valueOf(9000000, 2), bond.getBlockedBalance());

    }

}
